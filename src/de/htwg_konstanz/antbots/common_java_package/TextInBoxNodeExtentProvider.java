package de.htwg_konstanz.antbots.common_java_package;

import org.abego.treelayout.NodeExtentProvider;

/**
 * A {@link NodeExtentProvider} for nodes of type {@link TextInBox}.
 * <p>
 * As one would expect this NodeExtentProvider returns the width and height as
 * specified with each TextInBox.
 * 
 * @author Udo Borkowski (ub@abego.org)
 */
public class TextInBoxNodeExtentProvider implements
                NodeExtentProvider<TextInBox> {

        @Override
        public double getWidth(TextInBox treeNode) {
                return treeNode.width;
        }

        @Override
        public double getHeight(TextInBox treeNode) {
                return treeNode.height;
        }
}