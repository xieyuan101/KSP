package com.ksp.kspUtil;

import com.ksp.Graph;
import com.ksp.ksp.Eppstein;
import com.ksp.util.Path;

import java.util.List;

public class EppsteinUtil {
    public static List<Path> getOneODPair(String graphFilename, String source, String target, int k){
        Graph graph = new Graph(graphFilename);
        List<Path> ksp;
        Eppstein eppsteinAlgorithm = new Eppstein();
        ksp = eppsteinAlgorithm.ksp(graph, source, target, k);
        return ksp;
    }
}
