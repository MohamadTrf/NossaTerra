package com.br.nossaterra.controller

class Controller{
    fun clicaBotao(booleana: Boolean,vida:Float){
        var Vida : Float = vida
        var botão : Boolean = booleana
        if (botão == true) Vida++ else Vida--
    }
}
