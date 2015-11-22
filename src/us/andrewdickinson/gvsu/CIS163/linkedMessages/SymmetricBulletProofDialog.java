package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import javax.swing.*;
import java.awt.*;

/***********************************************************************
 * A BulletProofDialog that returns the same type of data that is input
 * Created by Andrew on 11/15/15.
 **********************************************************************/
public abstract class SymmetricBulletProofDialog<E>
        extends BulletProofDialog<E, E> {

    public SymmetricBulletProofDialog(Component parent,
                                      E preData){
        super(parent, preData);
    }
}
