scene = BUILDER._scene(){
    shotMat = _appearance( _color(0.5f), transparencyMode:screenDoor, transparency:0.9f);

    map("data/house/run.str", mapType:terain);

    control( startPosition: _point(x:-4.923f,y:4.003f,z:-0.399f), shotSource:_point(x:-0.033f,y: 1.301f,z: 0.588f), targetBhone:"joint3", disArmedBhone:"joint30", armedBhone:"joint24", armedTransform:_transform(x:-0.07f, y:-0.015f, z:0.044f, rotX:75.337f, rotY:-13.041f, rotZ:-84.067f), disArmedTransform:_transform(x:-0.097f, y:-0.116f, z:-0.027f, rotX:103.154f, rotY:0.387f, rotZ:4.691f) ){
        map("data/house/jump.str", mapType:jump);

        shot( appearance:shotMat, clip:"data/amy/sounds/shot.wav" )
        bhoneSkin( appearance:_appearance(texture:"data/amy/amy.png", alphaTestFunction:greater, mipMap:true, format:formatRGBA), bhoneFile:"data/amy/amy.bon", skinFile:"data/amy/amy.skn" ){
            frame("data/amy/keys/stand.ang", name:"STAND");
            frame("data/amy/keys/run1.ang", name:"RUN1", clip:"data/amy/sounds/step.wav"); frame("data/amy/keys/run2.ang", name:"RUN2"); frame("data/amy/keys/run3.ang", name:"RUN3", clip:"data/amy/sounds/step.wav"); frame("data/amy/keys/run4.ang", name:"RUN4");
            frame("data/amy/keys/jumpl1.ang", name:"JUMPL1", clip:"data/amy/sounds/jump.wav"); frame("data/amy/keys/jumpl2.ang", name:"JUMPL2"); frame("data/amy/keys/jumpl3.ang", name:"JUMPL3", clip:"data/amy/sounds/step.wav");
            frame("data/amy/keys/jumpr1.ang", name:"JUMPR1", clip:"data/amy/sounds/jump.wav"); frame("data/amy/keys/jumpr2.ang", name:"JUMPR2"); frame("data/amy/keys/jumpr3.ang", name:"JUMPR3", clip:"data/amy/sounds/step.wav");
            frame("data/amy/keys/rifleStand.ang", name:"RIFLE_STAND"); frame("data/amy/keys/down.ang", name:"DOWN");
            frame("data/amy/keys/rifleRun1.ang", name:"RIFLE_RUN1");  frame("data/amy/keys/rifleRun2.ang", name:"RIFLE_RUN2"); frame("data/amy/keys/rifleRun3.ang", name:"RIFLE_RUN3"); frame("data/amy/keys/rifleRun4.ang", name:"RIFLE_RUN4");
            frame("data/amy/keys/rifleFire.ang", name:"RIFLE_FIRE");  frame("data/amy/keys/riflePick.ang", name:"RIFLE_PICK");
            frame("data/amy/keys/deadShot.ang", name:"DEAD_SHOT", clip:"data/amy/sounds/die.wav");  frame("data/amy/keys/deadJump.ang", name:"DEAD_JUMP", clip:"data/amy/sounds/die.wav");
        };

        transformGroup(){ model(file:"data/amy/pistol/pistol.mod", appearance:_appearance(texture:"data/amy/pistol/pistol.png", mipMap:true )); };

        up = _animation( _point(x:0f,y: 1.278f,z: 0.509f) ){ for ( i in 1..8 ) animationFrame("data/amy/animations/up/"+i+".ang"); };
        down = _animation( _point(x:0f,y: -1.278f,z: 0.609f) ){ for ( i in 1..4 )animationFrame("data/amy/animations/down/"+i+".ang"); };

        animationTransform(up, source: _point(x:3.761f, y:0f, z:4.156f), sourceAngle:44.24f);
        animationTransform(up, source: _point(x:5.057f, y:0f, z:4.991f), sourceAngle:248.327f);
        animationTransform(down, source: _point(x:4.598f, y:1.278f, z:4.771f), sourceAngle:69.922f);
        animationTransform(down, source: _point(x:4.162f, y:1.278f, z:4.555f), sourceAngle:211.353f);

    };

    ai(){
        p90 = _shared(){ model(file:"data/soldier/p90/p90.mod", appearance:_appearance(texture:"data/soldier/p90/p90.png", mipMap:true)); }
        soldierMat = _appearance(texture:"data/soldier/soldier.png",mipMap:true);

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
                shot( appearance:shotMat, firePower:0.001f, cadence:500f, clip:"data/soldier/sounds/shot.wav" )
                bhoneSkin(appearance:soldierMat, bhoneFile:"data/soldier/soldier.bon", skinFile:"data/soldier/soldier.skn"){
                    frame("data/soldier/keys/stand.ang", name:"STAND");
                    frame("data/soldier/keys/run1.ang", name:"RUN1"); frame("data/soldier/keys/run2.ang", name:"RUN2"); frame("data/soldier/keys/run3.ang", name:"RUN3"); frame("data/soldier/keys/run4.ang", name:"RUN4");
                    frame("data/soldier/keys/rotate1.ang", name:"ROTATE1"); frame("data/soldier/keys/rotate2.ang", name:"ROTATE2");
                    frame("data/soldier/keys/dead.ang", name:"DEAD");
                }
                item(bhone:"joint24", transform:_transform(x:-0.144f, y:0.001f, z:0.03f, rotX:5.742f, rotY:-1.854f, rotZ:-119.718f)){ transformGroup(){ link(p90); }; };
                a.points();
            }
        };
    };

    KBpath( _time(time:45f), sceneType:sceneTypeEFFECT){
        key(0.0f    ,x: -7.288f, y: 2.347f, z: 13.291f, rotY:-90f );
        key(0.25f ,x: -4.449f, y: 5.319f, z: 6.605f, rotY:-100f );
        key(0.5f ,x: -3.032f, y: 5.832f, z: 0.92f, rotY:-100f );
        key(0.75f ,x: -1.828f, y: 5.172f, z: -5.289f, rotY:-100f );
        key(1.0f  ,x: -1.147f, y: 2.347f, z: -11.43f, rotY:-110f);

        wingTime = _time(time:0.2f, mode:incDec, ramp:0.02f);
        wingAxis = _transform(rotZ:90f);

        wing = _shared(){ model(file:"data/butterfly/wing.mod", appearance:_appearance(texture:"data/butterfly/butterfly.png",mipMap:true, cull:cullNone, alphaTestFunction:greater, format:formatRGBA )); }

        rotation(wingTime, transform:wingAxis, min:53f, max:105f ){ link(wing); };
        rotation(wingTime, transform:wingAxis, min:-53f, max:-105f ){ link(wing); };

        model(file:"data/butterfly/body.mod", appearance:_appearance(texture:"data/butterfly/butterfly.png",mipMap:true ));
    };

    model(file:"data/house/tools.mto", appearance:_appearance(){
        texture(texture:"data/house/tools.png",mipMap:true);
        texture(texture:"data/house/toolsLight.png",mipMap:true);
    });
    model(file:"data/house/outerWall.mto", appearance:_appearance(){
        texture(texture:"data/house/outerWall.png",mipMap:true);
        texture(texture:"data/house/outerWallLight.png",mipMap:true);
    });
    model(file:"data/house/ceiling.mto", appearance:_appearance(){
        texture(texture:"data/house/ceiling.png",mipMap:true);
        texture(texture:"data/house/ceilingLight.png",mipMap:true);
    });
    model(file:"data/house/wall.mto", appearance:_appearance(){
        texture(texture:"data/house/wall.png",mipMap:true);
        texture(texture:"data/house/wallLight.png",mipMap:true);
    });
    model(file:"data/house/floor.mto", appearance:_appearance(){
        texture(texture:"data/house/floor.png",mipMap:true);
        texture(texture:"data/house/floorLight.png", mipMap:true );
    });
    model(file:"data/house/brix.mto", appearance:_appearance(){
        texture(texture:"data/house/brix.png",mipMap:true);
        texture(texture:"data/house/brixLight.png", mipMap:true );
    });
    model(file:"data/house/road.mto", appearance:_appearance(){
        texture(texture:"data/house/road.png", mipMap:true );
        texture(texture:"data/house/roadLight.png", mipMap:true );
    });
    model(file:"data/house/trash.mto", appearance:_appearance(){
        texture(texture:"data/house/blueMetal.png",mipMap:true);
        texture(texture:"data/house/blueMetalLight.png",mipMap:true);
    });
    model(file:"data/house/wood.mto", appearance:_appearance(){
        texture(texture:"data/house/wood.png",mipMap:true);
        texture(texture:"data/house/woodLight.png",mipMap:true);
    });
    model(file:"data/house/barel.mto", appearance:_appearance(){
        texture(texture:"data/house/barel.png",mipMap:true);
        texture(texture:"data/house/barelLight.png",mipMap:true);
    });

    model(sceneType:sceneTypeEFFECT, file:"data/house/paprad.mod", appearance:_appearance(texture:"data/house/paprad.png",mipMap:true, format:formatRGBA, alphaTestFunction:greater, cull:cullNone ));

    model(file:"data/house/cloud.mod", appearance:_appearance(texture:"data/house/cloud.png",mipMap:true ));

    model(sceneType:sceneTypeCAMERA ,file:"data/house/skyBox.mod" , appearance:_appearance(texture:"data/house/skyBox.png", alphaTestFunction:greater, textureMode:replace, format:formatLUM4_ALPHA4, allowMaterial:false) );

    ambientLight( _color(0.4f));
    directionalLight( _color(0.6f) );

};
