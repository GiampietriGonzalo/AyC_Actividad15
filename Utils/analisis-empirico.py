# -*- coding: utf-8 -*-
"""
Created on Mon Jun 10 20:44:49 2019

@author: Martin
"""

import requests
import json

class Grafo:
    class Pesado:
        def __init__(self,arcoPeso):
            self.arco = arcoPeso[0]
            self.peso = arcoPeso[1]
            
    def __init__(self, n, a, cond = False):
        url = "http://cs.uns.edu.ar/~mom/AyC2019/grafo.php?nodos="+str(n)+"&arcos="+str(a)
        if cond:
            url += "&conexo=1"
        r = requests.get(url)
        grafo = json.loads(r.text)
        self.nodos = grafo['nodos']
        self.arcos = [self.Pesado(x) for x in grafo['arcos']]

    def getPrimerArco(self):
        return grafo.arcos[0].arco
    def getPesoPrimerArco(self):
        return grafo.arcos[0].peso


grafo = Grafo(500,6500)


"""
Generar varios grafos. 

Realizar análisis empírico mediante timestamps.
"""
