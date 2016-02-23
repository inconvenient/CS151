import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RunSearch implements ActionListener {

	// Button Declaration
	public JButton start;
	public JButton stop;
	public JButton search;

	// TextField and Windows Declaration
	public JTextField locationStartDirectory;
	public JTextField fileExtension;
	public JScrollPane listScroller;
	public JScrollPane fileScroller;
	public JTextArea fileView;
	public JFrame window;
	public JList<String> fileList;

	public void createGUI() {

		window = new JFrame("Java Find");
		window.setSize(1500, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());

		JPanel fileInfo = new JPanel();
		window.add(fileInfo, BorderLayout.SOUTH);

		JLabel startDirectory = new JLabel("Start Directory:");
		locationStartDirectory = new JTextField();
		locationStartDirectory.setEditable(true);
		locationStartDirectory.setPreferredSize(new Dimension(300, 20));
		fileInfo.add(startDirectory);
		fileInfo.add(locationStartDirectory);

		JLabel fileExtensionLabel = new JLabel("File Extension:");
		fileExtension = new JTextField();
		fileExtension.setEditable(true);
		fileExtension.setPreferredSize(new Dimension(300, 20));
		fileInfo.add(fileExtensionLabel);
		fileInfo.add(fileExtension);

		search = new JButton("Search");
		search.setPreferredSize(new Dimension(100, 20));
		fileInfo.add(search);

		stop = new JButton("Stop");
		stop.setPreferredSize(new Dimension(100, 20));
		stop.setVisible(false);
		fileInfo.add(stop);

		search.addActionListener(this);
		stop.addActionListener(this);

		fileView = new JTextArea();
		fileScroller = new JScrollPane(fileView);
		window.add(fileScroller, BorderLayout.CENTER);

		fileList = new JList<String>();
		listScroller = new JScrollPane(fileList);
		window.add(listScroller, BorderLayout.WEST);

		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// if search was clicked
		if (e.getSource().equals(search)) {
			search.setVisible(false); // Search button disappears
			stop.setVisible(true); // Stop button appears

			// Create the searcher and start the search thread
			Searcher search = new Searcher(new File(
					locationStartDirectory.getText()), fileExtension.getText());
			search.start();

			// Put results into ScrollPane
			String[] resArray = new String[search.getResults().size()];
			search.getResults().toArray(resArray);
			JList<String> files = new JList<String>(resArray);
			files.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					File selected = new File(files.getSelectedValue());
					try {
						// Calls readFile to read the selected file
						// Also adds code to the fileView textArea
						readFile(selected, fileView);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			// THIS UPDATES THE SEARCH!
			listScroller.setViewportView(files);
			listScroller.revalidate();
		}

		// if stop was clicked
		if (e.getSource().equals(stop)) {
			stop.setVisible(false);
			search.setVisible(true);

			locationStartDirectory.setEditable(true);
			fileExtension.setEditable(true);
		}
	}

	// Reads the file and writes it to TextArea in one method.
	public static void readFile(File f, JTextArea area) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		JTextArea textArea = area;
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			// Write the file content to the textArea 'fileView'
			textArea.setText(sb.toString());
		} finally {
			br.close();
		}
	}

	// Search Algorithm on Separate Thread
	class Searcher implements Runnable {

		private ArrayList<String> results = new ArrayList<String>();
		private File file;
		private String ext;
		private Thread searchThread;
		private boolean searching;

		// Constructor
		public Searcher(File f, String e) {
			file = f;
			ext = e;
		}

		// Start Call
		public void start() {
			searching = true;
			searchThread = new Thread(this);
			searchThread.run();
		}

		// Stop Call
		public void stop() {
			searching = false;
			if (searchThread != null) {
				searchThread.interrupt();
			}
		}

		@Override
		public void run() {
			results = searchInFiles(file, ext);
		}

		// Search Method
		public ArrayList<String> searchInFiles(File dir, String ext) {

			// File[] a = dir.listFiles();
			for (File f : dir.listFiles()) {
				if (f.isDirectory()) {
					searchInFiles(f, ext);
				} else if (f.getName().endsWith(ext)) {
					results.add(f.getAbsolutePath());
				}
			}
			return results;
		}

		public ArrayList<String> getResults() {
			return results;
		}
	}
}
