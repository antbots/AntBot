package de.htwg_konstanz.antbots.common_java_package;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;

import de.htwg_konstanz.antbots.treedrawer.TreeForTreeLayout;
import de.htwg_konstanz.antbots.treedrawer.TreeLayout;
import de.htwg_konstanz.antbots.treedrawer.util.DefaultConfiguration;



/**
 * Demonstrates how to use the {@link TreeLayout} to render a tree in a Swing
 * application.
 * <p>
 * Intentionally the sample code is kept simple. I.e. it does not include stuff
 * like anti-aliasing and other stuff one would add to make the output look
 * nice.
 * <p>
 * Screenshot:
 * <p>
 * <img src="doc-files/swingdemo.png">
 * 
 * @author Udo Borkowski (ub@abego.org)
 */
public class SwingDemo {

        private static void showInDialog(JComponent panel) {
                JDialog dialog = new JDialog();
                Container contentPane = dialog.getContentPane();
                ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(
                                10, 10, 10, 10));
                contentPane.add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
        }

        private static TreeForTreeLayout<TextInBox> getSampleTree(String treeName) {
                TreeForTreeLayout<TextInBox> tree;
                if (treeName.equals("2")) {
                        tree = SampleTreeFactory.createSampleTree2();
                } else if (treeName.equals("")) {
                        tree = SampleTreeFactory.createSampleTree();
                } else {
                        throw new RuntimeException(String.format("Invalid tree name: '%s'",
                                        treeName));
                }
                return tree;
        }

        /**
         * Shows a dialog with a tree in a layout created by {@link TreeLayout},
         * using the Swing component {@link TextInBoxTreePane}.
         */
        public static void main(String[] args) {
                // get the sample tree
                //String treeName = (args.length > 0) ? args[0] : "";
                TreeForTreeLayout<TextInBox> tree = getSampleTree("");
                                
                // setup the tree layout configuration
                double gapBetweenLevels = 50;
                double gapBetweenNodes = 10;
                DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
                                gapBetweenLevels, gapBetweenNodes);

                // create the NodeExtentProvider for TextInBox nodes
                TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

                // create the layout
                TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree,
                                nodeExtentProvider, configuration);

                // Create a panel that draws the nodes and edges and show the panel
                TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
                showInDialog(panel);
        }
}