import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import twitter4j.TwitterException;



public final class Main implements ActionListener {
    private BufferedImage image;               // the rasterized image
    private JFrame frame;                      // on-screen view
    private String filename;                   // name of file
    private boolean isOriginUpperLeft = true;  // location of origin
    private final int width, height;           // width and height

  
    public Main(Main pic) {
        width = pic.width();
        height = pic.height();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        filename = pic.filename;
        for (int x = 0; x < width(); x++)
            for (int y = 0; y < height(); y++)
                image.setRGB(x, y, pic.get(x, y).getRGB());
    }

   /**
     * Initializes a picture by reading in a .png, .gif, or .jpg from
     * the given filename or URL name.
     */
    public Main(String filename) {
        this.filename = filename;
        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }

            // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) { url = new URL(filename); }
                image = ImageIO.read(url);
            }
            width  = image.getWidth(null);
            height = image.getHeight(null);
        }
        catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }
    }

   /**
     * Initializes a picture by reading in a .png, .gif, or .jpg from a File.
     */
    public Main(File file) {
        try { image = ImageIO.read(file); }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not open file: " + file);
        }
        if (image == null) {
            throw new RuntimeException("Invalid image file: " + file);
        }
        width  = image.getWidth(null);
        height = image.getHeight(null);
        filename = file.getName();
    }

   /**
     * Returns a JLabel containing this picture, for embedding in a JPanel,
     * JFrame or other GUI widget.
     */
    public JLabel getJLabel() {
        if (image == null) { return null; }         // no image available
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

   /**
     * Sets the origin to be the upper left pixel.
     */
    public void setOriginUpperLeft() {
        isOriginUpperLeft = true;
    }

   /**
     * Sets the origin to be the lower left pixel.
     */
    public void setOriginLowerLeft() {
        isOriginUpperLeft = false;
    }

   /**
     * Displays the picture in a window on the screen.
     */
    public void show() {

        // create the GUI for viewing the image if needed
        if (frame == null) {
            frame = new JFrame();

            JMenuBar menuBar = new JMenuBar();
            JMenu file = new JMenu("File");
            JMenu filters = new JMenu("Filters");
            JMenu other = new JMenu("Others");
            JMenu online = new JMenu("Online");
            menuBar.add(file);
            menuBar.add(filters);
            menuBar.add(other);
            menuBar.add(online);
            
                    
            JMenuItem save = new JMenuItem(" Save...   ");
            JMenuItem open = new JMenuItem(" Open..");
            JMenuItem quit = new JMenuItem(" Quit.");
            JMenuItem sepia = new JMenuItem("Sepia");
            JMenuItem grayscale = new JMenuItem("Grayscale");
            JMenuItem invert = new JMenuItem("Invert");
            JMenuItem saturateH = new JMenuItem("Saturate High");
            JMenuItem saturateL = new JMenuItem("Saturate Low");
            JMenuItem border = new JMenuItem("Border");
            JMenuItem twitter = new JMenuItem("Post to Twitter");

            save.addActionListener(this);
            
            open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                     Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            file.add(open);
            file.add(save);
            file.add(quit);
            filters.add(sepia);
            filters.add(grayscale);
            filters.add(invert);
            filters.add(saturateH);
            filters.add(saturateL);
            other.add(border);
            online.add(twitter);
            twitter.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
			        FileDialog chooser = new FileDialog(frame,
                    "Use a .png or .jpg extension", FileDialog.LOAD);
			        chooser.setVisible(true);
			        if (chooser.getFile() != null) {
			        	try {
			        		System.out.print(chooser.getDirectory()+chooser.getFile());
							TwitBot.start(chooser.getDirectory()+chooser.getFile());
						} catch (TwitterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	}
				}
            	
            });

            border.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
			        try {
						Filters.applyBorder(image);
						frame.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
            	
            });
            sepia.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
			        try {
						Filters.applySepiaFilter(image, 1);
						frame.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
            	
            });
            saturateH.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
			        try {
						Filters.applySepiaFilter(image, 500);
						frame.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
            	
            });
            saturateL.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
			        try {
						Filters.applySepiaFilter(image, -500);
						frame.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
            	
            });
            //Opens a new file and initializes as a Picture object
            open.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
			        FileDialog chooser = new FileDialog(frame,
                    "Use a .png or .jpg extension", FileDialog.LOAD);
			        chooser.setVisible(true);
			        if (chooser.getFile() != null) {
			        	System.out.println(chooser.getDirectory()+ chooser.getFile());
			        open(chooser.getDirectory() + chooser.getFile());
			        	}
				}
            	
            });
            //Will quit the application
            quit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
		               System.exit(0);
				}
            	
            });
            grayscale.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
				
			      Filters.applyMonoChromeFilter(image);
			      frame.repaint();
				}
            	
            });
            invert.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Filters.applyInvert(image);
			         frame.repaint();
				}
            	
            });
            frame.setJMenuBar(menuBar);



            frame.setContentPane(getJLabel());
            // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setTitle("Photosnap: " + filename);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }

        // draw
        frame.repaint();
    }

   /**
     * Returns the height of the picture (in pixels).
     */
    public int height() {
        return height;
    }

   /**
     * Returns the width of the picture (in pixels).
     */
    public int width() {
        return width;
    }

 
    public Color get(int x, int y) {
        if (x < 0 || x >= width())  throw new IndexOutOfBoundsException("x must be between 0 and " + (width()-1));
        if (y < 0 || y >= height()) throw new IndexOutOfBoundsException("y must be between 0 and " + (height()-1));
        if (isOriginUpperLeft) return new Color(image.getRGB(x, y));
        else                   return new Color(image.getRGB(x, height - y - 1));
    }


    public void set(int x, int y, Color color) {
        if (x < 0 || x >= width())  throw new IndexOutOfBoundsException("x must be between 0 and " + (width()-1));
        if (y < 0 || y >= height()) throw new IndexOutOfBoundsException("y must be between 0 and " + (height()-1));
        if (color == null) throw new NullPointerException("can't set Color to null");
        if (isOriginUpperLeft) image.setRGB(x, y, color.getRGB());
        else                   image.setRGB(x, height - y - 1, color.getRGB());
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Main that = (Main) obj;
        if (this.width()  != that.width())  return false;
        if (this.height() != that.height()) return false;
        for (int x = 0; x < width(); x++)
            for (int y = 0; y < height(); y++)
                if (!this.get(x, y).equals(that.get(x, y))) return false;
        return true;
    }


   /**
     * Saves the picture to a file in a standard image format.
     * The filetype must be .png or .jpg.
     */
    public void save(String name) {
        save(new File(name));
    }

 //Save picture
    public void save(File file) {
        this.filename = file.getName();
        if (frame != null) { frame.setTitle(filename); }
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png")) {
            try { ImageIO.write(image, suffix, file); }
            catch (IOException e) { e.printStackTrace(); }
        }
        else {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }
    public void open(String name){
    	System.out.println(name);
    	Main pic = new Main(name);
    	pic.show();
    	 	  	
    }
    //Default save dialog
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame,
                             "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        if (chooser.getFile() != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }


    public static void main(String[] args) {
        Main pic = new Main("default.jpg");
        System.out.printf("%d-by-%d\n", pic.width(), pic.height());
        pic.show();
    }

}
