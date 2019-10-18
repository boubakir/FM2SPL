package util;
/**
 * Cette classe permat de mémoriser le contenu à édité d'un fichier ainsi que le chemin de ce fichier
 * @author  Administrateur
 */
public class FileToEdit {
	
	private String text;
	private String filePath;
	private String title;
	
	private boolean updated;
	public FileToEdit(String text, String filePath, String title, boolean updated){
		this.text = text;
		this.filePath = filePath;
		this.title = title;
		this.updated = updated;
	}

	public String getText(){	return this.text;	}
	public void setText(String text){	this.text = text;	}
	

	
	

	public String getFilePath(){
		return filePath;
	}

	public void setFilePath(String filePath){
		this.filePath = filePath;
	}


	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}
	

	public boolean isUpdated(){
		return this.updated;
	}


	public void setUpdated(boolean updated){
		this.updated = updated;
	}
}
