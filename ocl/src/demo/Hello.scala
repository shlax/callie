package demo

import java.nio.FloatBuffer
import java.util.Random
import com.jogamp.opencl.{CLDevice, CLPlatform, CLContext}

object Hello extends App{

  val code =
    """
      |// OpenCL Kernel Function for element by element vector addition
      |kernel void VectorAdd(global const float* a, global const float* b, global float* c, int numElements) {
      | // get index into global data array
      | int iGID = get_global_id(0);
      |
      | // bound check, equivalent to the limit on a 'for' loop
      | if (iGID >= numElements)  {
      |   return;
      | }
      |
      | // add the vector elements
      | for(int i = 0; i < 1000; i++){
      |   c[iGID] = a[iGID] * b[iGID];
      | }
      |}
    """.stripMargin

  def fillBuffer(buffer : FloatBuffer, seed:Int) {
    val rnd = new Random(seed)
    while(buffer.remaining() != 0) buffer.put(rnd.nextFloat()*100)
    buffer.rewind()
  }

  def roundUp(groupSize : Int, globalSize : Int) = {
    val  r = globalSize % groupSize
    if (r == 0) {
      globalSize
    } else {
      globalSize + groupSize - r
    }
  }

  // -----------------------------------------------------------------------------------------------------------------------------

//  CLPlatform [name: Intel(R) OpenCL, vendor: Intel(R) Corporation, profile: FULL_PROFILE, version: OpenCL 1.2 ]
//  CLPlatform [name: NVIDIA CUDA, vendor: NVIDIA Corporation, profile: FULL_PROFILE, version: OpenCL 1.1 CUDA 4.2.1]
  val d = CLPlatform.listCLPlatforms()
  d.foreach(println)

  // set up (uses default CLPlatform and creates context for all devices)
  val context = CLContext.create(d(0), CLDevice.Type.GPU)
  println("created "+context)

  // always make sure to release the context under all circumstances
  // not needed for this particular sample but recommented
  try{

    // select fastest device
    val device = context.getMaxFlopsDevice
    println("using "+device)

    // create command queue on device.
    val queue = device.createCommandQueue()

    val elementCount = 1444477                                  // Length of arrays to process
    val  localWorkSize = Math.min(device.getMaxWorkGroupSize, 256)  // Local work size dimensions
    val globalWorkSize = roundUp(localWorkSize, elementCount)   // rounded up to the nearest multiple of the localWorkSize

    // load sources, create and build program
    val program = context.createProgram(code).build()

    // A, B are input buffers, C is for the result
    val clBufferA = context.createFloatBuffer(globalWorkSize, com.jogamp.opencl.CLMemory.Mem.READ_ONLY)
    val clBufferB = context.createFloatBuffer(globalWorkSize, com.jogamp.opencl.CLMemory.Mem.READ_ONLY)
    val clBufferC = context.createFloatBuffer(globalWorkSize, com.jogamp.opencl.CLMemory.Mem.WRITE_ONLY)

    println("used device memory: " + (clBufferA.getCLSize+clBufferB.getCLSize+clBufferC.getCLSize)/1000000 +"MB")

    // fill input buffers with random numbers
    // (just to have test data; seed is fixed -> results will not change between runs).
    fillBuffer(clBufferA.getBuffer, 12345)
    fillBuffer(clBufferB.getBuffer, 67890)

    // get a reference to the kernel function with the name 'VectorAdd'
    // and map the buffers to its input parameters.
    val kernel = program.createCLKernel("VectorAdd")
    kernel.putArgs(clBufferA, clBufferB, clBufferC).putArg(elementCount)

    // asynchronous write of data to GPU device,
    // followed by blocking read to get the computed results back.
    var time = System.nanoTime()

    queue.putWriteBuffer(clBufferA, false).putWriteBuffer(clBufferB, false).put1DRangeKernel(kernel, 0, globalWorkSize, localWorkSize).putReadBuffer(clBufferC, true)

    time = System.nanoTime() - time

    // print first few elements of the resulting buffer to the console.
    println("a+b=c results snapshot: ")

    for(i <- 0 until 10) print(clBufferC.getBuffer.get() + ", ")

    println("...; " + clBufferC.getBuffer.remaining() + " more")

    println("computation took: "+(time/1000000)+"ms")
  }catch {
    case e : Exception=> e.printStackTrace()
  }finally{
    // cleanup all resources associated with this context.
    context.release()
  }

}
