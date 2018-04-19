package com.mygdx.game;


import com.badlogic.gdx.utils.Array;

import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.linear.AbstractRealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import java.util.Random;
import org.apache.commons.math3.linear.OpenMapRealMatrix;


/**
 * Created by danielli on 1/9/18.
 * SML network
 */

public class NeuralNet {
    private Array<LayerTransition> layerTransitions;

    public NeuralNet(int[] neuronLayerSizes){
        layerTransitions=new Array<LayerTransition>();
        for(int i=0; i<neuronLayerSizes.length-1;i++){
            LayerTransition transition=new LayerTransition(neuronLayerSizes[i],neuronLayerSizes[i+1]);
            layerTransitions.add(transition);
        }
    }

    public NeuralNet mutate(){
        return null;
    }

    public double[] backPropagate(double[] inputs){
        double[] value=inputs;
        for(int i=0; i<layerTransitions.size;i++){
            value=layerTransitions.get(i).sigmoidTransition(value);
        }
        return value;
    }

    private class LayerTransition{
        private AbstractRealMatrix weightMapping;
        private double[] bias;

        public LayerTransition(int inputSize, int outputSize){
            Random rand = new Random();

            double[][] weights= new double[inputSize][outputSize];
            for(int i=0;i<inputSize;i++){
                for(int j=0; j< inputSize;i++){
                    weights[i][j]=rand.nextDouble()*2-1;
                }
            }
            weightMapping=new Array2DRowRealMatrix(weights);
            bias=new double[outputSize];
            for(int i=0; i<bias.length;i++){
                bias[i]=rand.nextDouble()*2-1;
            }

            /**
             * TODO: EASY BUG SOURCE
             */

        }

        public double[] sigmoidTransition(double[] input){
            return add(weightMapping.preMultiply(input),bias);
        }

        public double[] add(double[] a, double[] b){
            if(a.length!=b.length){
                throw new ArrayIndexOutOfBoundsException();
            }
            double[] c= new double[a.length];
            for(int i=0; i<a.length;i++){
                c[i]=a[i]+b[i];
            }
            return c;
        }
    }

}
