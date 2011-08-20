scene = BUILDER._scene(){
    map("house/run.str", mapType:terain);

    control( startPosition: _point(x:-4.923f,y:4.003f,z:-0.399f), shotSource:_point(x:-0.033f,y: 1.301f,z: 0.588f), targetBhone:"joint3", disArmedBhone:"joint30", armedBhone:"joint24", armedTransform:_transform(x:-0.07f, y:-0.015f, z:0.044f, rotX:75.337f, rotY:-13.041f, rotZ:-84.067f), disArmedTransform:_transform(x:-0.097f, y:-0.116f, z:-0.027f, rotX:103.154f, rotY:0.387f, rotZ:4.691f) ){
        map("house/jump.str", mapType:jump);

        shot(clip:"amy/sounds/shot.wav", appearance:_appearance(), firePower:1f );
        bhoneSkin( appearance:_appearance(texture:"amy/amy.png", alphaTest:true), bhoneFile:"amy/amy.bon", skinFile:"amy/amy.skn" ){
            frame("amy/keys/stand.ang", name:"STAND");
            frame("amy/keys/run1.ang", name:"RUN1", clip:"amy/sounds/step.wav"); frame("amy/keys/run2.ang", name:"RUN2"); frame("amy/keys/run3.ang", name:"RUN3", clip:"amy/sounds/step.wav"); frame("amy/keys/run4.ang", name:"RUN4");
            frame("amy/keys/jumpl1.ang", name:"JUMPL1", clip:"amy/sounds/jump.wav"); frame("amy/keys/jumpl2.ang", name:"JUMPL2"); frame("amy/keys/jumpl3.ang", name:"JUMPL3", clip:"amy/sounds/step.wav");
            frame("amy/keys/jumpr1.ang", name:"JUMPR1", clip:"amy/sounds/jump.wav"); frame("amy/keys/jumpr2.ang", name:"JUMPR2"); frame("amy/keys/jumpr3.ang", name:"JUMPR3", clip:"amy/sounds/step.wav");
            frame("amy/keys/rifleStand.ang", name:"RIFLE_STAND"); frame("amy/keys/down.ang", name:"DOWN");
            frame("amy/keys/rifleRun1.ang", name:"RIFLE_RUN1");  frame("amy/keys/rifleRun2.ang", name:"RIFLE_RUN2"); frame("amy/keys/rifleRun3.ang", name:"RIFLE_RUN3"); frame("amy/keys/rifleRun4.ang", name:"RIFLE_RUN4");
            frame("amy/keys/rifleFire.ang", name:"RIFLE_FIRE");  frame("amy/keys/riflePick.ang", name:"RIFLE_PICK");
            frame("amy/keys/deadShot.ang", name:"DEAD_SHOT", clip:"amy/sounds/die.wav");  frame("amy/keys/deadJump.ang", name:"DEAD_JUMP", clip:"amy/sounds/die.wav");
        };

        weapon(){ model(file:"amy/pistol/pistol.mod", appearance:_appearance(texture:"amy/pistol/pistol.png")); };

        up = _animation( _point(x:0f,y: 1.278f,z: 0.509f) ){ for ( i in 1..8 ) frame("amy/animations/up/"+i+".ang"); };
        down = _animation( _point(x:0f,y: -1.278f,z: 0.609f) ){ for ( i in 1..4 )frame("amy/animations/down/"+i+".ang"); };

        animationTransform(up, source: _point(x:3.761f, y:0f, z:4.156f), sourceAngle:44.24f);
        animationTransform(up, source: _point(x:5.057f, y:0f, z:4.991f), sourceAngle:248.327f);
        animationTransform(down, source: _point(x:4.598f, y:1.278f, z:4.771f), sourceAngle:69.922f);
        animationTransform(down, source: _point(x:4.162f, y:1.278f, z:4.555f), sourceAngle:211.353f);

        shotLeft = _animation( _point(x:-0.495f, y:1.295, z:-0.58), sourceRadius:0.25f, shotAngle:180f, side:-2.67f, frameWindow:0.225f, targetPosition:_point(x:-0.483f, y:1.304f, z:0.03f) ){
            frame("amy/covers/left/0.ang");
            frame("amy/covers/left/1.ang");
            frame("amy/covers/left/2.ang", shot:true);
            frame("amy/covers/left/1.ang");
        };

        shotRight = _animation( _point(x:0.539f, y:1.294, z:-0.581), sourceRadius:0.25f, shotAngle:180f, side:2.67f, frameWindow:0.225f, targetPosition:_point(x:0.483f, y:1.304f, z:0.03f) ){
            frame("amy/covers/right/0.ang");
            frame("amy/covers/right/1.ang");
            frame("amy/covers/right/2.ang", shot:true);
            frame("amy/covers/right/1.ang");
        };

        animationTransform(shotLeft, source: _point(x:-3.739f, y:4.003f, z:0.724f), sourceAngle:-180f);
        animationTransform(shotLeft, source: _point(x:-1.961f, y:4.003f, z:3.178f), sourceAngle:-90f);
        animationTransform(shotLeft, source: _point(x:6.717f, y:4.003f, z:4.292f), sourceAngle:90f);
        animationTransform(shotLeft, source: _point(x:6.976f, y:-0.008f, z:0.72f), sourceAngle:-180f);
        animationTransform(shotLeft, source: _point(x:-4.111f, y:-0.008f, z:1.35f), sourceAngle:0f);
        animationTransform(shotLeft, source: _point(x:-1.962f, y:-0.008f, z:3.159f), sourceAngle:-90f);

        animationTransform(shotRight, source: _point(x:-3.748f, y:4.003f, z:1.345f), sourceAngle:0f);
        animationTransform(shotRight, source: _point(x:6.098f, y:4.003f, z:4.29f), sourceAngle:-90f);
        animationTransform(shotRight, source: _point(x:6.721f, y:-0.008f, z:3.164f), sourceAngle:90f);
        animationTransform(shotRight, source: _point(x:-1.345f, y:-0.008f, z:3.164f), sourceAngle:90f);
        animationTransform(shotRight, source: _point(x:-4.111f, y:-0.008f, z:0.729f), sourceAngle:-180f);
    };

    ai(){
        p90 = _appearance(texture:"soldier/p90/p90.png");
        soldierMat = _appearance(texture:"soldier/soldier.png");

        [
            [start: _point(x:-3.158f, y:4.003f, z:5.425f), points:{} ],  // zlty
            [start: _point(x:7.728f, y:-0.008f, z:-2.871f), points:{  // modry
                check(x:-3.619f, y:-0.008f, z:2.096f);
                check(x:-7.305f, y:-0.008f, z:-3.576f);
                check(x:10.1f, y:-0.008f, z:-6.62f);
                check(x:7.728f, y:-0.008f, z:-2.871f);
            }],
            [start: _point(x:7.674f, y:4.003f, z:4.568f), points:{ // cerveny
                check(x:9.968f, y:-0.008f, z:4.568f);
                check(x:-7.075f, y:-0.008f, z:7.547f);
                check(x:-7.289f, y:-0.008f, z:-6.61f);
                check(x:10.1f, y:-0.008f, z:-6.62f);
                check(x:7.674f, y:4.003f, z:4.568f);
            }],
            [start: _point(x:10.1f, y:-0.008f, z:-6.62f), points:{ // orange
                check(x:9.935f, y:-0.008f, z:7.745f);
                check(x:-7.075f, y:-0.008f, z:7.547f);
                check(x:-7.289f, y:-0.008f, z:-6.61f);
                check(x:10.1f, y:-0.008f, z:-6.62f);
            }],
            [start: _point(x:-5.552f, y:-0.008f, z:-7.289f), points:{} ]  // purple
        ].each(){ a ->
            agent(shotSource: _point(x:-0.065f, y:1.322f, z:0.588f), lookAtSource: _point(x:-0.027f, y:1.66f, z:0.154f), startPosition:a.start){
                shot( appearance:_appearance(), firePower:1f, cadence:0.5f, clip:"soldier/sounds/shot.wav" )
                bhoneSkin(appearance:soldierMat, bhoneFile:"soldier/soldier.bon", skinFile:"soldier/soldier.skn"){
                    frame("soldier/keys/stand.ang", name:"STAND");
                    frame("soldier/keys/run1.ang", name:"RUN1"); frame("soldier/keys/run2.ang", name:"RUN2"); frame("soldier/keys/run3.ang", name:"RUN3"); frame("soldier/keys/run4.ang", name:"RUN4");
                    frame("soldier/keys/rotate1.ang", name:"ROTATE1"); frame("soldier/keys/rotate2.ang", name:"ROTATE2");
                    frame("soldier/keys/dead.ang", name:"DEAD");
                }
                item(bhone:"joint24", transform:_transform(x:-0.144f, y:0.001f, z:0.03f, rotX:5.742f, rotY:-1.854f, rotZ:-119.718f)){
                    model(file:"soldier/p90/p90.mod", appearance:p90);
                };
                a.points();
            }
        };
    };

    group(sceneType:sceneTypeEFFECT, transform:_transform(rotY:-100f) ){
        interpolator(repeatType:wrap, speed:0.4f){
            point(x: -7.288f, y: 2.347f, z: 13.291f);
            point(x: -7.288f, y: 2.347f, z: 13.291f);
            point(x: -4.449f, y: 5.319f, z: 6.605f);
            point(x: -3.032f, y: 5.832f, z: 0.92f);
            point(x: -1.828f, y: 5.172f, z: -5.289f);
            point(x: -1.147f, y: 2.347f, z: -11.43f);
            point(x: -1.147f, y: 2.347f, z: -11.43f);
        }

        model(file:"butterfly/body.mod", appearance:_appearance(texture:"butterfly/butterfly.png"));
        model(file:"butterfly/wing.mod", appearance:_appearance(texture:"butterfly/butterfly.png", cull:false, alphaTest:true)){
           interpolator(repeatType:cycle, speed:2.5f){
                rotate(x:-1.6f);
                rotate(x:-0.6f);
            }
        };
        model(file:"butterfly/wing.mod", appearance:_appearance(texture:"butterfly/butterfly.png", cull:false, alphaTest:true)){
            interpolator(repeatType:cycle, speed:2.5f){
                rotate(x:1.6f);
                rotate(x:0.6f);
            }
        };
    };

    model(file:"house/tools.mto", appearance:_appearance(texture:["house/tools.png", "house/toolsLight.png"]) );
    model(file:"house/outerWall.mto", appearance:_appearance(texture:["house/outerWall.png", "house/outerWallLight.png"]) );
    model(file:"house/ceiling.mto", appearance:_appearance(texture:["house/ceiling.png", "house/ceilingLight.png"]) );
    model(file:"house/wall.mto", appearance:_appearance(texture:["house/wall.png", "house/wallLight.png"]) );
    model(file:"house/floor.mto", appearance:_appearance(texture:["house/floor.png", "house/floorLight.png"]) );
    model(file:"house/brix.mto", appearance:_appearance(texture:["house/brix.png", "house/brixLight.png"]) );
    model(file:"house/road.mto", appearance:_appearance(texture:["house/road.png", "house/roadLight.png"]) );
    model(file:"house/trash.mto", appearance:_appearance(texture:["house/blueMetal.png", "house/blueMetalLight.png"]) );
    model(file:"house/wood.mto", appearance:_appearance(texture:["house/wood.png", "house/woodLight.png"]) );
    model(file:"house/barel.mto", appearance:_appearance(texture:["house/barel.png", "house/barelLight.png"]) );

    model(file:"house/cloud.mod", appearance:_appearance(texture:"house/cloud.png"));

    model(sceneType:sceneTypeEFFECT, file:"house/paprad.mod", appearance:_appearance(texture:"house/paprad.png", cull:false, alphaTest:true ));
    model(sceneType:sceneTypeCAMERA ,file:"house/skyBox.mod" , appearance:_appearance(texture:"house/skyBox.png", alphaTest:true) );

};
