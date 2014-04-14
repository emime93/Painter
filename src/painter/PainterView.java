/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package painter;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import static javax.swing.Spring.height;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author petricioiurobert
 */
public class PainterView implements ChangeListener{

    JFrame frame;
    JPanel panel;
    JPanel leftPanel;
    JPanel rightPanel;
    GridBagConstraints gbc;
    Picture board;
    PaintTools tool;
    BufferedImage bi;

    Icon roundBrushIcon;

    public void loadResources() {
        roundBrushIcon = (Icon) frame.getToolkit().getImage("");
    }

    public PainterView() {
        frame = new JFrame("Painter");
        frame.setPreferredSize(new Dimension(910, 700));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        panel = new JPanel(new FlowLayout());
        frame.add(panel);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Save");
        JMenuItem menuItem1 = new JMenuItem("Load");
        
        gbc = new GridBagConstraints();
        tool = new PaintTools(PaintTools.Tool.Brush);
        tool.setTool(PaintTools.Tool.SquareBrush);
        
        bi = new BufferedImage(650, 700, BufferedImage.TYPE_INT_RGB);

        addComponents();

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new File("image.png"));
                    int option = fileChooser.showSaveDialog(panel);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        String fileName = fileChooser.getSelectedFile().getName();
                        System.out.println("-" + fileName.substring(fileName.indexOf(".")+1, fileName.length()) + "-");
                        ImageIO.write(bi, fileName.substring(fileName.indexOf(".")+1, fileName.length()), fileChooser.getSelectedFile());
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(PainterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        menuItem1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new File("image.png"));
                    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Images only","JPG","PNG","JPEG");
                    fileChooser.setFileFilter(fileFilter);
                    
                    int option = fileChooser.showOpenDialog(panel);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        String fileName = fileChooser.getSelectedFile().getName();
                        BufferedImage bf = ImageIO.read(fileChooser.getSelectedFile());
                        tool.g.drawImage(bf, 0, 0, null);
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(PainterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        menu.add(menuItem);
        menu.add(menuItem1);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);
        tool.setGraphics((Graphics2D) board.getGraphics(), bi);
        
    }

    public void addComponents() {
        leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.gray);
        leftPanel.setPreferredSize(new Dimension(250, 700));

        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridx = 1;
        gbc.gridy = 1;

        JButton roundBrushButton = new JButton();
        ImageIcon brushIcon = null;
        ImageIcon squareBrushIcon = null;
        ImageIcon rectangleIcon = null;
        ImageIcon circleIcon = null;
        ImageIcon lineIcon = null;
        ImageIcon fillIcon = null;
        try {
            brushIcon = new ImageIcon(ImageIO.read(getClass().getResource("brush-icon.png")));
            squareBrushIcon = new ImageIcon(ImageIO.read(getClass().getResource("square-brush.png")));
            rectangleIcon = new ImageIcon(ImageIO.read(getClass().getResource("rectangle-icon.png")));
            circleIcon = new ImageIcon(ImageIO.read(getClass().getResource("circle-icon.png")));
            lineIcon = new ImageIcon(ImageIO.read(getClass().getResource("line-icon.png")));
            fillIcon = new ImageIcon(ImageIO.read(getClass().getResource("fill-icon.png")));
           
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        roundBrushButton.setIcon(brushIcon);
        roundBrushButton.setPreferredSize(new Dimension(80, 80));
        roundBrushButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tool.setTool(PaintTools.Tool.Brush);
            }
        });
        
        gbc.anchor = GridBagConstraints.NORTH;
        leftPanel.add(roundBrushButton, gbc);

        JButton squareBrushButton = new JButton();
        squareBrushButton.setIcon(squareBrushIcon);
        squareBrushButton.setPreferredSize(new Dimension(80, 80));
        squareBrushButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tool.setTool(PaintTools.Tool.SquareBrush);
            }
        });

        gbc.gridx = 2;

        leftPanel.add(squareBrushButton, gbc);

        JButton paintCircleButton = new JButton();
        paintCircleButton.setPreferredSize(new Dimension(80, 80));
        paintCircleButton.setIcon(circleIcon);
        paintCircleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tool.setTool(PaintTools.Tool.Circle);
            }
        });
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        leftPanel.add(paintCircleButton, gbc);
        
        JButton fillButton = new JButton();
        fillButton.setPreferredSize(new Dimension(80, 80));
        fillButton.setIcon(fillIcon);
        fillButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tool.fillScreen();
            }
        });
        
        gbc.gridx = 3;
        gbc.gridy = 2;
        leftPanel.add(fillButton, gbc);

        JButton paintRectangleButton = new JButton();
        paintRectangleButton.setPreferredSize(new Dimension(80, 80));
        paintRectangleButton.setIcon(rectangleIcon);
        paintRectangleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tool.setTool(PaintTools.Tool.Rectangle);
            }
        });

        gbc.gridx = 3;
        gbc.gridy = 1;
        leftPanel.add(paintRectangleButton, gbc);

        JButton increaseButton = new JButton();
        increaseButton.setPreferredSize(new Dimension(80, 80));
        increaseButton.setIcon(lineIcon);
        increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tool.setTool(PaintTools.Tool.Line);
            }
        });


        gbc.gridx = 1;
        gbc.gridy = 2;
        leftPanel.add(increaseButton, gbc);

        JPanel palletePanel = new JPanel(new FlowLayout());
        palletePanel.setBackground(Color.lightGray);
        palletePanel.setPreferredSize(new Dimension(80, 240));
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(palletePanel, gbc);

        JButton[] colorButtons = new JButton[28];

        Color[] colors = {Color.BLACK, Color.WHITE, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN,
            Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW
        };
        String[] colorNames = {"Black", "White", "Blue", "Cyan", "Dark gray", "Gray", "Green", "Magenta", "Orange", "Pink", "Red", "Yellow"};
        for (int i = 0; i < colors.length; ++i) {
            colorButtons[i] = new JButton();
            colorButtons[i].setPreferredSize(new Dimension(50, 50));
            final Color c = colors[i];
            colorButtons[i].setBackground(c);
            colorButtons[i].setOpaque(true);
            colorButtons[i].setBorderPainted(false);
            colorButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tool.setColor(c);
                }
            });
            palletePanel.add(colorButtons[i]);
        }
        
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 1);
        slider.addChangeListener(this);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        palletePanel.add(slider);
        
        panel.add(leftPanel);

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setPreferredSize(new Dimension(650, 700));
        board = new Picture(tool,bi);
        rightPanel.add(board);

        panel.add(rightPanel);
    }
    public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
        int fps = (int)source.getValue();
        if (fps != 0){
            tool.setSize(fps);
        }
    }
}

   
}
