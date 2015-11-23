package us.andrewdickinson.gvsu.CIS163.linkedMessages.clipboardInteraction;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.prefs.Preferences;

/***********************************************************************
 * Used to work around a linux clipboard bug
 * Saves/Recalls the clip board using the preferences api
 * Created by Andrew on 11/23/15.
 **********************************************************************/
public class ClipBoard {
    /*******************************************************************
     * Saves the current clipboard to a preferences file
     ******************************************************************/
    public static void saveClipBoard(){
        Clipboard clipBoard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();

        Transferable t = clipBoard.getContents(null);
        try {
            if (t.isDataFlavorSupported(DataFlavor.stringFlavor)){
                String data = t.getTransferData(DataFlavor.stringFlavor)
                        .toString();
                final Preferences prefs = Preferences.userRoot()
                        .node(ClipBoard.class.getName());
                prefs.put("DATA", data);
            }
        } catch (IOException|UnsupportedFlavorException e){
            //Should never occur
            //Throw a lesser exception
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Sets the clipboard based on the saved one
     ******************************************************************/
    public static void retreiveClipBoard(){
        final Preferences prefs = Preferences.userRoot()
                .node(ClipBoard.class.getName());
        String data = prefs.get("DATA", "");

        if (!data.equals("")){
            Clipboard clipBoard = Toolkit.getDefaultToolkit()
                    .getSystemClipboard();
            StringSelection ss = new StringSelection(data);
            clipBoard.setContents(ss, ss);
        }
    }
}
