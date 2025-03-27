package com.kreitek.editor;

import java.util.ArrayList;

public class Memento {
    private final ArrayList<String> documentState;

    public Memento(ArrayList<String>documentState){
    this.documentState = new ArrayList<>(documentState);
    }

    public ArrayList<String> getDocumentState(){
        return documentState;
    }
}
