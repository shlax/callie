package ts;

import java.io.*;
import java.util.ArrayList;

public class BinGeometry {

    public static void main(String[] args) throws Exception{
        process(new File("data/house"));
        process(new File("data/amy"));
        process(new File("data/soldier"));
        process(new File("data/butterfly"));
    }

    private static void process(File f) throws Exception{
        if(f.isDirectory()){
            for(File ff : f.listFiles()) process(ff);
        }else {
            if(f.getName().endsWith("mod") || f.getName().endsWith("mto"))geom(f);
            else if(f.getName().endsWith("skn"))skin(f);
            else if(f.getName().endsWith("bon"))bhone(f);
            else if(f.getName().endsWith("ang"))frame(f);
            else if(f.getName().endsWith("str"))map(f);
        }
    }

    private static void map(File f) throws Exception{
        DataOutputStream os = new DataOutputStream(new FileOutputStream(new File(f.getParentFile(), f.getName()+".bin")));
        BufferedReader bi = new BufferedReader( new InputStreamReader( new FileInputStream(f)) );

		String tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
        os.writeInt(Integer.parseInt(tmp));
        //Point3f vertices[] = new Point3f[];

        tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
        //int j = 0;
        for(String i : tmp.split("\\]\\[")){

			String xyz[] = i.split("\\,");
			os.writeFloat(Float.parseFloat(xyz[0]));
            os.writeFloat(Float.parseFloat(xyz[1]));
            os.writeFloat(Float.parseFloat(xyz[2]));
            //vertices[j] = new Point3f();
         //   j++;
		}

		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
        os.writeInt(Integer.parseInt(tmp));
        //LoadedTriangle triangles[] = new LoadedTriangle[Integer.parseInt(tmp)];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
        //j = 0;
		for(String i : tmp.split("\\}\\{")){
			String abc[] = i.split("\\,");
            os.writeInt(Integer.parseInt(abc[0]));
            os.writeInt(Integer.parseInt(abc[1]));
            os.writeInt(Integer.parseInt(abc[2]));
			//triangles[j] = new LoadedTriangle( vertices[ Integer.parseInt(abc[0]) ], vertices[ Integer.parseInt(abc[1]) ], vertices[ Integer.parseInt(abc[2])], t );
          //  j++;
        }

		bi.close();
        os.close();
    }


    private static class FrameData{
        String name;

        float x;
        float y;
        float z;

        float ax;
        float ay;
        float az;
    }

    private static void frame(File f) throws Exception{
        DataOutputStream os = new DataOutputStream(new FileOutputStream(new File(f.getParentFile(), f.getName()+".bin")));
        BufferedReader bi = new BufferedReader( new InputStreamReader( new FileInputStream(f) ) );

        ArrayList<FrameData> tmp = new ArrayList<FrameData>();

        String l;
        boolean first = true;
        while((l = bi.readLine()) != null){
            FrameData fd = new FrameData();
            String p[] =l.split("\\ ");
            if(p.length > 0){
                int off = 1;

                if(first){
                    // float x = Float.parseFloat(p[1]);
                    fd.x = Float.parseFloat(p[1]);
                    //float y = Float.parseFloat(p[2]);
                    fd.y = Float.parseFloat(p[2]);
                    //float z = Float.parseFloat(p[3]);
                    fd.z = Float.parseFloat(p[3]);

                //    bgl = new LoadedBhoneFrame(new Vector3f(x,y,z));
                    off = 4;
                    first = false;
                }

                fd.ax = (float)((Float.parseFloat(p[off])*Math.PI)/180);
                fd.ay = (float)((Float.parseFloat(p[1+off])*Math.PI)/180);
                fd.az = (float)((Float.parseFloat(p[2+off])*Math.PI)/180);

                fd.name = p[0];

                tmp.add(fd);
               // bgl.addAngle(p[0], new Angle3f(x,y,z));
            }
        }

        os.writeInt(tmp.size());


        for(int i = 0; i < tmp.size(); i++){
            FrameData fd = tmp.get(i);
            os.writeUTF(fd.name);
            if(i == 0){
                os.writeFloat(fd.x);
                os.writeFloat(fd.y);
                os.writeFloat(fd.z);
            }

            os.writeFloat(fd.ax);
            os.writeFloat(fd.ay);
            os.writeFloat(fd.az);
        }

        bi.close();
        os.close();
    }

    private static void bhone(File f) throws Exception{
        DataOutputStream os = new DataOutputStream(new FileOutputStream(new File(f.getParentFile(), f.getName()+".bin")));
        BufferedReader bi = new BufferedReader( new InputStreamReader( new FileInputStream(f) ) );
        bi.readLine(); // skip first
        processBhone(os, bi);
        bi.close();
        os.close();
    }
    private static void processBhone(DataOutputStream os, BufferedReader bi) throws Exception{

        String tmp = bi.readLine();
		String p[] = tmp.split("\\ ");
		String name = p[0];

        os.writeUTF(name);

     //   System.out.println(tmp);

		boolean linear = "linear".equals(p[1]);
        //LinearBhone.Maping map[] =  linear ? new LinearBhone.Maping[]{LinearBhone.Maping.X, LinearBhone.Maping.Y, LinearBhone.Maping.Z} : null ;
        os.writeBoolean(linear);

		/* float mx = Float.parseFloat(p[2]);
		float my = Float.parseFloat(p[3]);
		float mz = Float.parseFloat(p[4]); */

        os.writeFloat(Float.parseFloat(p[2]));
        os.writeFloat(Float.parseFloat(p[3]));
        os.writeFloat(Float.parseFloat(p[4]));

        os.writeByte((p.length - 5) / 2);

        for(int i = 5; i < p.length; i += 2){
            if("x".equals(p[i])){
                //ind = 0;
                os.writeByte(0);
            }else if("y".equals(p[i])){
                //ind = 1;
                os.writeByte(1);
            }else if("z".equals(p[i])){
                //ind = 2;
                os.writeByte(2);
            }

           // LinearBhone.Maping val = null;
            if("x".equals(p[i+1])){
                //val = LinearBhone.Maping.X;
                os.writeByte(0);
            }else if("y".equals(p[i+1])){
                //val = LinearBhone.Maping.Y;
                os.writeByte(1);
            }else if("z".equals(p[i+1])){
                //val = LinearBhone.Maping.Z;
                os.writeByte(2);
            }
        }

		tmp = bi.readLine();
		p = tmp.split("\\,");

        os.writeInt(p.length);
        //int index[] = new int[p.length];
		for(int i = 0; i < p.length; i++){
            //index[i] = Integer.parseInt(p[i]);
            os.writeInt( Integer.parseInt(p[i]) );
        }

        //LoadedBhone.Type type = linear ? LoadedBhone.Type.Linear : LoadedBhone.Type.Acelerated;
        //Vector3f mov = new Vector3f(mx*, my*, mz*);
        //LoadedBhone abc = new LoadedBhone(name, mov, /*parent,*/ index, type, map);

        tmp = bi.readLine();
        boolean end = "end".equals(tmp);
        os.writeBoolean(end);

		while( !end ){
            processBhone(os, bi);


            tmp = bi.readLine();
            end = "end".equals(tmp);
            os.writeBoolean(end);
            //abc.addLoadedBhone(loadBone(abc, bi));
        }

    }

    private static void skin(File f) throws Exception{
        DataOutputStream os = new DataOutputStream(new FileOutputStream(new File(f.getParentFile(), f.getName()+".bin")));
        BufferedReader bi = new BufferedReader( new InputStreamReader( new FileInputStream(f) ) );

		String tmp;


		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		//Point3f vertices[] = new Point3f[Integer.parseInt(tmp)];
        os.writeInt( Integer.parseInt(tmp) );

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		//j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			//vertices[j] = new Point3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
            os.writeFloat( Float.parseFloat(xyz[0]) );
            os.writeFloat( Float.parseFloat(xyz[1]) );
            os.writeFloat( Float.parseFloat(xyz[2]) );
		//	j ++;
		}

		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		//TexCoord2f texCoords[] = new TexCoord2f[Integer.parseInt(tmp)];
        os.writeInt( Integer.parseInt(tmp) );

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		//j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
		//	texCoords[j] = new TexCoord2f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]));
            os.writeFloat( Float.parseFloat(xyz[0]) );
            os.writeFloat( Float.parseFloat(xyz[1]) );
		//	j++;
		}

		// T O D O : normal start
		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];

	//	Vector3f normals[] = new Vector3f[Integer.parseInt(tmp)];
        os.writeInt( Integer.parseInt(tmp) );

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);

		//j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
		//	normals[j] = new Vector3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
            os.writeFloat( Float.parseFloat(xyz[0]) );
            os.writeFloat( Float.parseFloat(xyz[1]) );
            os.writeFloat( Float.parseFloat(xyz[2]) );
	//		j ++;
		}

		// faces
		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		int j = Integer.parseInt(tmp);
        os.writeInt( j );

		//int coordIndexes[] = new int[j*3];
		//int textureIndexes[] = new int[j*3];
		//int normalIndexes[] = new int[j*3];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		String arr[] = tmp.split("\\}\\{");

        //HashMap<Integer, ArrayList<Integer>> vertexNormal = new HashMap<Integer, ArrayList<Integer>>();

		for(int i = 0; i < j; i++){
			arr[i] = arr[i].substring(1, arr[i].length() - 1);
			String face[] = arr[i].split("\\]\\[");
			for(int k = 0; k < 3; k ++){
			//	System.out.println(face[k]);
				String cnt[] = face[k].split("\\,");
//				coordIndexes[(i*3)+k] = Integer.parseInt(cnt[0]);
//				textureIndexes[(i*3)+k] = Integer.parseInt(cnt[1]);
//				normalIndexes[(i*3)+k] = Integer.parseInt(cnt[2]);
				os.writeInt( Integer.parseInt(cnt[0]) );
				os.writeInt( Integer.parseInt(cnt[1]) );
				os.writeInt( Integer.parseInt(cnt[2]) );

				//addKeyFrameObj(vertexNormal, coordIndexes[(i*3)+k], normalIndexes[(i*3)+k]);
			}
		}

		bi.close();
        os.close();
    }

    private static void geom(File f) throws Exception{
        boolean mto = f.getName().endsWith(".mto");

        DataOutputStream os = new DataOutputStream(new FileOutputStream(new File(f.getParentFile(), f.getName()+".bin")));
        BufferedReader bi = new BufferedReader( new InputStreamReader( new FileInputStream(f) ) );
		String tmp;
	//	int j;

		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];

        // Point3f vertices[] = new Point3f[Integer.parseInt(tmp)];
        os.writeInt(Integer.parseInt(tmp));


		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
	//	j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			//vertices[j] = new Point3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
            os.writeFloat(Float.parseFloat(xyz[0]));
            os.writeFloat(Float.parseFloat(xyz[1]));
            os.writeFloat(Float.parseFloat(xyz[2]));
		//	j ++;
		}

		//for(Point3f r : vertices)System.out.println(r);

        int coordCount = 1;

        //TexCoord2f texCoords[][];
        //ArrayList<TexCoord2f> coordList[] = null;

        if(mto){
            tmp = bi.readLine();
		    tmp  = tmp.split("\\=")[1];
		    coordCount = Integer.parseInt(tmp);
            os.writeInt(coordCount);
            //texCoords = new TexCoord2f[coordCount][];
            //coordList = new ArrayList[coordCount];
            //for(int i = 0; i < coordCount; i++) coordList[i] = new ArrayList<TexCoord2f>();
        }/*else {
            //texCoords = new TexCoord2f[1][];
            //texCoords[0] = texCoordsAll;
        }*/

        for(int l = 0; l < coordCount; l++){
            tmp = bi.readLine();
		    tmp  = tmp.split("\\=")[1];

            //texCoords[l] = new TexCoord2f[Integer.parseInt(tmp)];
            os.writeInt(Integer.parseInt(tmp));

            tmp = bi.readLine();
            tmp = tmp.substring(1, tmp.length() - 1);

         //   j = 0;
            for(String i : tmp.split("\\]\\[")){
                String xyz[] = i.split("\\,");
                //texCoords[l][j] = new TexCoord2f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]));
                os.writeFloat(Float.parseFloat(xyz[0]));
                os.writeFloat(Float.parseFloat(xyz[1]));
            //    j++;
            }
        }

		//for(TexCoord2f r : texCoords)System.out.println(r);

		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];

		//Vector3f normals[] = new Vector3f[Integer.parseInt(tmp)];
        os.writeInt(Integer.parseInt(tmp));

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);

		//j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			//normals[j] = new Vector3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
            os.writeFloat(Float.parseFloat(xyz[0]));
            os.writeFloat(Float.parseFloat(xyz[1]));
            os.writeFloat(Float.parseFloat(xyz[2]));
		//	j++;
		}

		//for(Vector3f r : normals)System.out.println(r);

		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		int j = Integer.parseInt(tmp);

        os.writeInt(j);

//		int coordIndexes[] = new int[j*3];
//		int textureIndexes[][] = new int[coordCount][j*3];
//		int normalIndexes[] = new int[j*3];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		String arr[] = tmp.split("\\}\\{");

		for(int i = 0; i < j; i++){
			arr[i] = arr[i].substring(1, arr[i].length() - 1);
			String face[] = arr[i].split("\\]\\[");
			for(int k = 0; k < 3; k ++){
				String cnt[] = face[k].split("\\,");
				//coordIndexes[(i*3)+k] = Integer.parseInt(cnt[0]);
                os.writeInt( Integer.parseInt(cnt[0]) );
				// texture
                if(mto){
                    String txIndex[] = cnt[1].substring(1, cnt[1].length() -1).split("\\;");
                    for(int l = 0; l < coordCount; l++){
                        /*TexCoord2f coord = texCoordsAll[Integer.parseInt(txIndex[l])];
                        ArrayList<TexCoord2f> al = coordList[l];
                        int ind = al.indexOf(coord);
                        if(ind == -1){
                            ind = al.size();
                            al.addKeyFrameObj(coord);
                        }*/
                        //textureIndexes[l][(i*3)+k] = Integer.parseInt(txIndex[l]);
                        os.writeInt( Integer.parseInt(txIndex[l]) );
                    }
                }else{
                    //textureIndexes[0][(i*3)+k] = Integer.parseInt(cnt[1]);
                    os.writeInt( Integer.parseInt(cnt[1]) );
                }
				//normalIndexes[(i*3)+k] = Integer.parseInt(cnt[2]);
                os.writeInt( Integer.parseInt(cnt[2]) );
			}
		}

		bi.close();
        os.close();
    }
}
