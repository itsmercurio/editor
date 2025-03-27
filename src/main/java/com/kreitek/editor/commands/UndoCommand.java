package com.kreitek.editor.commands;

import com.kreitek.editor.Command;
import com.kreitek.editor.ConsoleEditor;
import com.kreitek.editor.Memento;

import java.util.ArrayList;

public class UndoCommand implements Command {
    private final ConsoleEditor editor;

    public UndoCommand(ConsoleEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute(ArrayList<String> documentLines) {
        if (!editor.getHistory().isEmpty()) {
            Memento lastMemento = editor.getHistory().pop();
            documentLines.clear();
            documentLines.addAll(lastMemento.getDocumentState());
            System.out.println("Undo successful. Restored previous state.");
        } else {
            System.out.println("No actions to undo.");
        }
    }
}
