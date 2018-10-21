//104403533 ���_�� ���2B
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.nio.*;

public class Pokemon extends JFrame {
	private int[] limit = new int[3]; //�i�Ʃһݪ��}�G��
	private Icon[] picture = new ImageIcon[3];//�s����ɪ�Icon
	private JLabel myPet;//���_�i�ڹϤ���Label
	private JTextField nicknameField;//�_�i�ڼʺ�
	private JPanel buttonArea1;//���_�i�ڼʺ١B���s��Panel
	private JPanel buttonArea2;//����s��Panel
	private JPanel funtionArea;//��buttonArea1�BbuttonArea2��Panel
	private JButton giveCandy;//�W�[�}�G�ƪ����s
	private JButton openGame;//�}�����ɪ����s
	private JButton save;//�s�ɪ����s
	private JButton saveAs;//�t�s�s�ɪ����s
	private JButton degenerate;//�_�i�ڰh�ƫ��s
	private JLabel candyNumber;//��ܥثe�}�G�ƩM�i�Ʊ��󪺫��s
	private JLabel filePath;//����ɮ׸��|��Label
	private Scanner input;
	private int counter;//�}�G��
	private int level;//�_�i�ڵ���
	private String monster = "";//�_�i�ں���
	private File fileName; 
	private ObjectInputStream obinput;
	private PokeSerializable record;
	
	public Pokemon(){
		super("�_�i��~~");
		setLayout(new FlowLayout());
		monster = "�p���s";
		myPet = new JLabel();
		 try{
			 input = new Scanner(new File(this.getClass().getResource("/pokemon.txt").getPath()));//Ū�Jpokemon.txt
		     
		    int i=0;
		    while(input.hasNext()){
		     picture[i] = new ImageIcon(getClass().getResource(input.next()));//�s�J�_�i�ڹϤ�
		     if(i<=1){
		    	 limit[i] = input.nextInt();//�s�J�_�i�ڶi�Ʃһݿ}�G��
		    	 }
		     i++;
		    }	
		    }
		    catch(NoSuchElementException elementException){
		    	System.err.println("File improperly Form.");
		    	input.close();
		    	System.exit(1);
		    }
		    catch(IllegalStateException stateException){
		    	System.err.println("Error reading from file");
		    	System.exit(1);
		    }
		    catch(FileNotFoundException fileNotFoundException){
		    	System.err.println("�䤣���ɮ�");
		    	System.exit(1);
		    }
		    catch (IOException e) {
		    	System.err.println("Ū�ɮ׮ɵo�{���~");
		    	System.exit(1);
			}
		    finally{
		    	if(input !=null)
		    		input.close();
		    }
		  
		limit[2] = limit[1];
		myPet.setIcon(picture[0]);//�e�{�_�i�ڪ��Ϥ�
		add(myPet);
		
		nicknameField = new JTextField("set your nickname",36);

		buttonArea1 = new JPanel();
		buttonArea2 = new JPanel();
	
		giveCandy = new JButton("Give Candy");
		CandyHandler candyHandler = new CandyHandler();
		giveCandy.addActionListener(candyHandler);
		buttonArea1.add(giveCandy);
		
		candyNumber = new JLabel("0/25");
		buttonArea1.add(candyNumber);
		
		degenerate = new JButton("Degenerate"); 
		buttonArea1.add(degenerate);
		
		openGame = new JButton("Open Game");
		OpenGameHandler openGameHandler = new OpenGameHandler();
		openGame.addActionListener(openGameHandler);
		buttonArea2.add(openGame);
		
		save = new JButton("Save");
		SaveHandler saveHandler = new SaveHandler();
		save.addActionListener(saveHandler);
		buttonArea2.add(save);
		
		saveAs = new JButton("Save As");
		SaveAsHandler saveAsHandler = new SaveAsHandler();
		saveAs.addActionListener(saveAsHandler);
		buttonArea2.add(saveAs);
		
		filePath = new JLabel("New File ");
		
		funtionArea = new JPanel();
		funtionArea.setLayout(new GridLayout(4,1));
		funtionArea.add(nicknameField);
		funtionArea.add(buttonArea1);
		funtionArea.add(buttonArea2);
		funtionArea.add(filePath);
		
		add(funtionArea);
	
	}
	private class CandyHandler implements ActionListener{//���sGiveCandy�ҥΪ�Handler�A�Ω��_�i�ڿ}�G
		public void actionPerformed(ActionEvent event){
			counter = counter+1;//�}�G�ƥ[�@
			if(counter==limit[0]&& level==0)//�P�_�}�G�ƬO�_�F��i�Ƭ������s������
			{
				JOptionPane.showMessageDialog(null, "�i�Ʀ������s");
				level = level+1;//���ť[�@
				monster = "�����s";//�_�i�ں����ܬ������s
				counter = 0;//�}�G���k�s
			}
			else if(counter ==limit[1]&& level ==1){//�P�_�}�G�ƬO�_�F��i�Ƭ��Q���s������
				JOptionPane.showMessageDialog(null, "�i�Ʀ��Q���s�A���ߧA�����_�i�ڤj�v!");
				level = level+1;//���ť[�@
				monster = "�Q���s";//�_�i�ں����ܬ��Q���s
			}
				candyNumber.setText(String.format("%d/%d",counter,limit[level]));//��scandyNumber�ҧe�{����T
				myPet.setIcon(picture[level]);//��s�_�i�ڹϤ�
		}
	}
	private class DegenerateHandler implements ActionListener{//���sDegenerate��Handler�A�Ω����_�i�ڰh��
		public void actionPerformed(ActionEvent event){
			if(level!=0){//�P�_�_�i�گ�_�h��
				level = level-1;//���Ŵ�@
				JOptionPane.showMessageDialog(Pokemon.this, "�_�i�ڰh��,�}�G���k�s");
			}else{
				JOptionPane.showMessageDialog(Pokemon.this, "�L�k�h��,�}�G���k�s");
			}
			counter = 0;//�}�G���k�s
			candyNumber.setText(String.format("%d/%d",counter,limit[level]));//��scandyNumber�ҧe�{����T
			myPet.setIcon(picture[level]);//��s�_�i�ڹϤ�
				
		}
	}
	private class OpenGameHandler implements ActionListener{//���sOpenGame�ҥΪ�Handler�A�Ω�}������
		public void actionPerformed(ActionEvent event){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int result = fileChooser.showOpenDialog(Pokemon.this);
			if(result==JFileChooser.CANCEL_OPTION)
				System.exit(1);
			fileName = fileChooser.getSelectedFile();//�N�ҿ諸�ɮצs�JfileName
			filePath.setText(fileName.getAbsolutePath());//�e�{�ɮת�������|
			try{
			obinput = new ObjectInputStream(new FileInputStream(fileName));//�}������
			record = (PokeSerializable) obinput.readObject();//�j���ഫ��PokeSerializable
			counter = record.getCandy();//�N���ɪ��}�G�Ʀs��counter
			monster= record.getMonster();//�N���ɪ��_�i�ں����s�Jmonster
			nicknameField.setText(record.getNickname());//��s�_�i�ڼʺ�
			
			switch(record.getMonster()){//�P�_�_�i�ں����A�]�w����
			case "�p���s":
				level = 0;
				break;
			case "�����s":
				level = 1;
				break;
			case "�Q���s":
				level = 2;
				break;
			}
			
			candyNumber.setText(String.format("%d/%d",counter,limit[level]));//��scandyNumber�ҧe�{����T
			myPet.setIcon(picture[level]);//��s�_�i�ڹϤ�
			
				input.close();
			}
			catch (IOException e) {
				System.err.println("Ū�ɮ׮ɵo�{���~");
			}
			catch(ClassNotFoundException classNotFoundException){
				System.err.println("�L�k�إߪ���");	
		    }	
			
		}
	}
	private class SaveAsHandler implements ActionListener{//���sSaveAs�ҥΪ�Handler�A�Ω�t�s�s��
		public void actionPerformed(ActionEvent event){
			PokeSerializable newRecord = new PokeSerializable(nicknameField.getText(),monster,counter);//create new record

			try{
				JFileChooser directoryChooser = new JFileChooser();
				directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = directoryChooser.showSaveDialog(Pokemon.this);
				if(result==JFileChooser.CANCEL_OPTION)
					System.exit(1);
				
				String newFileName = JOptionPane.showInputDialog(null,"�п�J�ɦW(�ݧt���ɦW)");
				File getDirectory = directoryChooser.getSelectedFile();
			
			//open file	
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(getDirectory.getAbsolutePath()+"/"+newFileName));
			output.writeObject(newRecord);//output record
			output.close();
			filePath.setText(getDirectory.getAbsolutePath()+"/"+newFileName);//��sfilePath�ҧe�{���ɮ׸��|
			
			}
			catch(IOException e) {
				System.err.println("�s�ɮ׮ɵo�{���~");
			}
		}
	}
	private class SaveHandler implements ActionListener{//���sSave�ҥΪ�Handler�A�Ω�s��
		public void actionPerformed(ActionEvent event){
			record = new PokeSerializable(nicknameField.getText(),monster,counter);
			
			try{
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
				output.writeObject(record);
				output.close();
				}
				catch(NullPointerException e){
					JOptionPane.showMessageDialog(Pokemon.this, "�D���ɡA�n�s�ɽЫ�SaveAs");
					
				}
				catch(IOException e) {
					System.err.println("�s�ɮ׮ɵo�{���~");
				}
		}
	}
	
}
