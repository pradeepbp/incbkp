import javax.swing.*;


class DemoGUI{
	
	public static void main(String[] args){
			NewWindow appWindow = new NewWindow("Demo Window");
			appWindow.createGUI();
	}
		
} // end of main class

class NewWindow{
	
	String windowTitle;
	public NewWindow(String title){
		windowTitle = title;
	}
	
	public void createGUI(){
		JFrame frame = new JFrame(windowTitle);
		frame.getContentPane().add(new JLabel("Test"));
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
		
}