/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.constraintlayout.core.dsl;

import static org.junit.Assert.assertEquals;

import androidx.constraintlayout.core.parser.CLParser;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.state.CorePixelDp;
import androidx.constraintlayout.core.state.TransitionParser;

import org.junit.Test;

public class DslTest {
    //  test structures
    static CorePixelDp dipToDip = new CorePixelDp() {

        @Override
        public float toPixels(float dp) {
            return dp;
        }
    };
    static androidx.constraintlayout.core.state.Transition transitionState =
            new androidx.constraintlayout.core.state.Transition();


    @Test
    public void testTransition01() {
        MotionScene motionScene = new MotionScene();
        motionScene.addTransition(new Transition("start", "end"));
        System.out.println(motionScene);
        String exp = "{\n"
                + "Transitions:{\n"
                + "default:{\n"
                + "from:'start',\n"
                + "to:'end',\n"
                + "}\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testTransition02() {
        MotionScene motionScene = new MotionScene();
        motionScene.addTransition(new Transition("expand", "start", "end"));
        System.out.println(motionScene);
        String exp = "{\n"
                + "Transitions:{\n"
                + "expand:{\n"
                + "from:'start',\n"
                + "to:'end',\n"
                + "}\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testOnSwipe01() throws CLParsingException {
        MotionScene motionScene = new MotionScene();
        Transition transition = new Transition("expand", "start", "end");
        transition.setOnSwipe(new OnSwipe());
        motionScene.addTransition(transition);

        System.out.println(motionScene);
        String exp = "{\n"
                + "Transitions:{\n"
                + "expand:{\n"
                + "from:'start',\n"
                + "to:'end',\n"
                + "OnSwipe:{\n"
                + "}\n"
                + "}\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());

        TransitionParser.parse(CLParser.parse(transition.toString()), transitionState, dipToDip);
    }

    @Test
    public void testOnSwipe02() throws CLParsingException {
        MotionScene motionScene = new MotionScene();
        Transition transition = new Transition("expand", "start", "end");
        transition.setOnSwipe(new OnSwipe("button", OnSwipe.Side.RIGHT, OnSwipe.Drag.RIGHT));
        motionScene.addTransition(transition);

        System.out.println(motionScene);
        String exp = "{\n"
                + "Transitions:{\n"
                + "expand:{\n"
                + "from:'start',\n"
                + "to:'end',\n"
                + "OnSwipe:{\n"
                + "anchor:'button',\n"
                + "direction:'right',\n"
                + "side:'right',\n"
                + "}\n"
                + "}\n"
                + "}\n"
                + "}\n";

        assertEquals(exp, motionScene.toString());

        TransitionParser.parse(CLParser.parse(transition.toString()), transitionState, dipToDip);
    }

    @Test
    public void testOnKeyPosition01() throws CLParsingException {
        MotionScene motionScene = new MotionScene();
        Transition transition = new Transition("expand", "start", "end");
        transition.setOnSwipe(new OnSwipe("button", OnSwipe.Side.RIGHT, OnSwipe.Drag.RIGHT));
        KeyPosition kp = new KeyPosition("button", 32);
        kp.setPercentX(0.5f);
        transition.setKeyFrames(kp);
        motionScene.addTransition(transition);

        System.out.println(motionScene);
        String exp = "{\n"
                + "Transitions:{\n"
                + "expand:{\n"
                + "from:'start',\n"
                + "to:'end',\n"
                + "OnSwipe:{\n"
                + "anchor:'button',\n"
                + "direction:'right',\n"
                + "side:'right',\n"
                + "}\n"
                + "keyFrames:{\n"
                + "KeyPositions:{\n"
                + "target:'button',\n"
                + "frame:32,\n"
                + "type:'CARTESIAN',\n"
                + "percentX:0.5,\n"
                + "}\n"
                + "}\n"
                + "}\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());

        String formattedJson = CLParser.parse(motionScene.toString()).toFormattedJSON();
        String fomatExp = "{\n"
                + "  Transitions: {\n"
                + "    expand: {\n"
                + "      from: 'start',\n"
                + "      to: 'end',\n"
                + "      OnSwipe: { anchor: 'button', direction: 'right', side: 'right' },\n"
                + "      keyFrames: {\n"
                + "        KeyPositions: {\n"
                + "          target:           'button',\n"
                + "          frame:           32,\n"
                + "          type:           'CARTESIAN',\n"
                + "          percentX:           0.5\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}";

        assertEquals(fomatExp, formattedJson);

        TransitionParser.parse(CLParser.parse(transition.toString()), transitionState, dipToDip);
    }

    @Test
    public void testOnKeyPositions0() throws CLParsingException {
        MotionScene motionScene = new MotionScene();
        Transition transition = new Transition("expand", "start", "end");
        transition.setOnSwipe(new OnSwipe("button", OnSwipe.Side.RIGHT, OnSwipe.Drag.RIGHT));
        KeyPositions kp = new KeyPositions(2,"button1","button2");

        transition.setKeyFrames(kp);
        motionScene.addTransition(transition);

        System.out.println(motionScene);
        String exp = "{\n"
                + "Transitions:{\n"
                + "expand:{\n"
                + "from:'start',\n"
                + "to:'end',\n"
                + "OnSwipe:{\n"
                + "anchor:'button',\n"
                + "direction:'right',\n"
                + "side:'right',\n"
                + "}\n"
                + "keyFrames:{\n"
                + "KeyPositions:{\n"
                + "target:['button1','button2'],\n"
                + "frame:[33, 66],\n"
                + "}\n"
                + "}\n"
                + "}\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());

        String formattedJson = CLParser.parse(motionScene.toString()).toFormattedJSON();
        String fomatExp = "{\n"
                + "  Transitions: {\n"
                + "    expand: {\n"
                + "      from: 'start',\n"
                + "      to: 'end',\n"
                + "      OnSwipe: { anchor: 'button', direction: 'right', side: 'right' },\n"
                + "      keyFrames: { KeyPositions: { target: ['button1', 'button2'], frame: [33,"
                + " 66] } }\n"
                + "    }\n"
                + "  }\n"
                + "}";

        assertEquals(fomatExp, formattedJson);

        TransitionParser.parse(CLParser.parse(transition.toString()), transitionState, dipToDip);
    }

    @Test
    public void testAnchor01() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet = new ConstraintSet("start");
        Constraint constraint = new Constraint("a");
        Constraint constraint2 = new Constraint("b");
        constraintSet.add(constraint);
        constraint.left.mConnection = constraint2.left;
        motionScene.addConstraintSet(constraintSet);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "left:['b','left'],\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testAnchor02() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet = new ConstraintSet("start");
        Constraint constraint = new Constraint("a");
        Constraint constraint2 = new Constraint("b");
        constraintSet.add(constraint);
        constraint.left.mConnection = constraint2.left;
        constraint.left.mMargin = 15;
        motionScene.addConstraintSet(constraintSet);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "left:['b','left',15],\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testAnchor03() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet = new ConstraintSet("start");
        Constraint constraint = new Constraint("a");
        Constraint constraint2 = new Constraint("b");
        Constraint constraint3 = new Constraint("c");
        Constraint constraint4 = new Constraint("d");
        constraintSet.add(constraint);
        constraint.left.mConnection = constraint2.right;
        constraint.left.mMargin = 5;
        constraint.left.mGoneMargin = 10;
        constraint.top.mConnection = constraint3.bottom;
        constraint.top.mGoneMargin = 15;
        constraint.baseline.mConnection = constraint4.baseline;
        motionScene.addConstraintSet(constraintSet);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "left:['b','right',5,10],\n"
                + "top:['c','bottom',0,15],\n"
                + "baseline:['d','baseline'],\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testConstraint01() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet = new ConstraintSet("start");
        Constraint constraint = new Constraint("a");
        constraint.setHeight(0);
        constraint.setWidth(40);
        constraint.dimensionRatio = "1:1";
        constraint.editorAbsoluteX = 15;
        constraint.editorAbsoluteY = 25;
        constraintSet.add(constraint);
        motionScene.addConstraintSet(constraintSet);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "width:40,\n"
                + "height:0,\n"
                + "dimensionRatio:'1:1',\n"
                + "editorAbsoluteX:15,\n"
                + "editorAbsoluteY:25,\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testConstraint02() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet = new ConstraintSet("start");
        Constraint constraint = new Constraint("a");
        constraint.widthPercent = 50;
        constraint.heightPercent = 60;
        constraint.horizontalBias = 0.3f;
        constraint.verticalBias = 0.2f;
        constraint.circleConstraint = "parent";
        constraint.circleRadius = 10;
        constraint.verticalWeight = 2.1f;
        constraint.horizontalWeight = 1f;
        constraintSet.add(constraint);
        motionScene.addConstraintSet(constraintSet);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "horizontalBias:0.3,\n"
                + "verticalBias:0.2,\n"
                + "circular:['parent',0,10],\n"
                + "verticalWeight:2.1,\n"
                + "horizontalWeight:1.0,\n"
                + "width:'50%',\n"
                + "height:'60%',\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testConstraint03() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet = new ConstraintSet("start");
        Constraint constraint = new Constraint("a");
        constraint.widthDefault = Constraint.Behaviour.WRAP;
        constraint.heightDefault = Constraint.Behaviour.SPREAD;
        constraint.widthMax = 30;
        constraint.widthMin = 10;
        constraint.circleConstraint = "parent";
        constraint.circleAngle = 10;
        constraint.mReferenceIds = new String[] {"a", "b", "c"};
        Constraint constraint2 = new Constraint("b");
        constraint2.horizontalChainStyle = Constraint.ChainMode.SPREAD_INSIDE;
        constraint2.verticalChainStyle = Constraint.ChainMode.PACKED;
        constraint2.constrainedWidth = true;
        constraint2.constrainedHeight = true;
        constraintSet.add(constraint);
        constraintSet.add(constraint2);
        motionScene.addConstraintSet(constraintSet);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "circular:['parent',10.0],\n"
                + "width:{value:'wrap',max:30,min:10},\n"
                + "height:'spread',\n"
                + "mReferenceIds:['a','b','c'],\n"
                + "},\n"
                + "b:{\n"
                + "horizontalChainStyle:'spread_inside',\n"
                + "verticalChainStyle:'packed',\n"
                + "constrainedWidth:true,\n"
                + "constrainedHeight:true,\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());

    }

    @Test
    public void testConstraintSet01() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet1 = new ConstraintSet("start");
        ConstraintSet constraintSet2 = new ConstraintSet("end");
        Constraint constraint1 = new Constraint("a");
        Constraint constraint2 = new Constraint("b");
        Constraint constraint3 = new Constraint("c");
        constraintSet1.add(constraint1);
        constraintSet1.add(constraint2);
        constraintSet2.add(constraint3);
        motionScene.addConstraintSet(constraintSet1);
        motionScene.addConstraintSet(constraintSet2);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "},\n"
                + "b:{\n"
                + "},\n"
                + "},\n"
                + "end:{\n"
                + "c:{\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }

    @Test
    public void testConstraintSet02() {
        MotionScene motionScene = new MotionScene();
        ConstraintSet constraintSet1 = new ConstraintSet("start");
        ConstraintSet constraintSet2 = new ConstraintSet("end");
        Constraint constraint1 = new Constraint("a");
        Constraint constraint2 = new Constraint("b");
        Constraint constraint3 = new Constraint("a");
        Constraint constraint4 = new Constraint("b");
        constraint1.setWidth(50);
        constraint1.setHeight(60);
        constraint2.setWidth(30);
        constraint2.setHeight(0);
        constraint2.dimensionRatio = "1:1";
        constraint1.left.mConnection = constraint2.left;
        constraint1.left.mMargin = 10;
        constraint1.right.mConnection = constraint2.right;
        constraint1.right.mGoneMargin = 15;
        constraintSet1.add(constraint1);
        constraint3.heightPercent = 40;
        constraint3.widthPercent = 30;
        constraint4.setHeight(20);
        constraint4.setHeight(30);
        constraint4.widthDefault = Constraint.Behaviour.SPREAD;
        constraint4.heightDefault = Constraint.Behaviour.WRAP;
        constraint4.heightMax = 100;
        constraint4.heightMin = 80;
        constraint4.top.mConnection = constraint3.top;
        constraint4.top.mMargin = 5;
        constraint4.top.mGoneMargin = 10;
        constraint4.bottom.mConnection = constraint3.bottom;
        constraintSet1.add(constraint2);
        constraintSet2.add(constraint3);
        constraintSet2.add(constraint4);
        motionScene.addConstraintSet(constraintSet1);
        motionScene.addConstraintSet(constraintSet2);
        System.out.println(motionScene);
        String exp = "{\n"
                + "ConstraintSets:{\n"
                + "start:{\n"
                + "a:{\n"
                + "left:['b','left',10],\n"
                + "right:['b','right',0,15],\n"
                + "width:50,\n"
                + "height:60,\n"
                + "},\n"
                + "b:{\n"
                + "width:30,\n"
                + "height:0,\n"
                + "dimensionRatio:'1:1',\n"
                + "},\n"
                + "},\n"
                + "end:{\n"
                + "a:{\n"
                + "width:'30%',\n"
                + "height:'40%',\n"
                + "},\n"
                + "b:{\n"
                + "top:['a','top',5,10],\n"
                + "bottom:['a','bottom'],\n"
                + "height:30,\n"
                + "width:'spread',\n"
                + "height:{value:'wrap',max:100,min:80},\n"
                + "},\n"
                + "},\n"
                + "}\n"
                + "}\n";
        assertEquals(exp, motionScene.toString());
    }
}
