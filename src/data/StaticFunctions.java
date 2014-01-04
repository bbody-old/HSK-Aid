package data;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class StaticFunctions {
	// Standard Bounds for the program
	public static Rectangle bounds = new Rectangle(100, 100, 450, 300);
	
	public static enum fileCheck{
		OK,
		NULL,
		NOTHING,
		EXTENSION,
		NOT_EXIST
	};
	

	public static String getFile(JFrame frame){
		JFileChooser chooser = new JFileChooser();
		
		FileFilter ff = new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				if (arg0.isDirectory()){
					return true;
				}
				return arg0.getName().endsWith(".xls");
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Excel Documents";
			}};
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(ff);
	    int returnVal = chooser.showOpenDialog(frame);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " + //$NON-NLS-1$
	            chooser.getSelectedFile().getName());
	    }
	    String filename;
	    try {
	    	filename = chooser.getSelectedFile().getAbsolutePath();
	    } catch (NullPointerException e){
	    	return null;
	    }
	    
	    return filename;
	    
	}
	
	public static fileCheck filenameCheck(String filename){
		if (filename == null){
			return fileCheck.NULL;
		} else if (!(filename.length() > 0)){
			return fileCheck.NOTHING;
		} else if (!filename.endsWith(".xls")){ //$NON-NLS-1$
			System.out.println(filename.substring(filename.length() - 3, filename.length()));
			return fileCheck.EXTENSION;
		} else if (!new File(filename).exists()){
			return fileCheck.NOT_EXIST;
		}else {
			return fileCheck.OK;
		}
	}
	
}
