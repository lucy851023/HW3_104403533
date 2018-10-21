//104403533 莊于萱 資管2B
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.nio.*;

public class Pokemon extends JFrame {
	private int[] limit = new int[3]; //進化所需的糖果數
	private Icon[] picture = new ImageIcon[3];//存放圖檔的Icon
	private JLabel myPet;//放寶可夢圖片的Label
	private JTextField nicknameField;//寶可夢暱稱
	private JPanel buttonArea1;//放寶可夢暱稱、按鈕的Panel
	private JPanel buttonArea2;//放按鈕的Panel
	private JPanel funtionArea;//放buttonArea1、buttonArea2的Panel
	private JButton giveCandy;//增加糖果數的按鈕
	private JButton openGame;//開啟舊檔的按鈕
	private JButton save;//存檔的按鈕
	private JButton saveAs;//另存新檔的按鈕
	private JButton degenerate;//寶可夢退化按鈕
	private JLabel candyNumber;//顯示目前糖果數和進化條件的按鈕
	private JLabel filePath;//顯示檔案路徑的Label
	private Scanner input;
	private int counter;//糖果數
	private int level;//寶可夢等級
	private String monster = "";//寶可夢種類
	private File fileName; 
	private ObjectInputStream obinput;
	private PokeSerializable record;
	
	public Pokemon(){
		super("寶可夢~~");
		setLayout(new FlowLayout());
		monster = "小火龍";
		myPet = new JLabel();
		 try{
			 input = new Scanner(new File(this.getClass().getResource("/pokemon.txt").getPath()));//讀入pokemon.txt
		     
		    int i=0;
		    while(input.hasNext()){
		     picture[i] = new ImageIcon(getClass().getResource(input.next()));//存入寶可夢圖片
		     if(i<=1){
		    	 limit[i] = input.nextInt();//存入寶可夢進化所需糖果數
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
		    	System.err.println("找不到檔案");
		    	System.exit(1);
		    }
		    catch (IOException e) {
		    	System.err.println("讀檔案時發現錯誤");
		    	System.exit(1);
			}
		    finally{
		    	if(input !=null)
		    		input.close();
		    }
		  
		limit[2] = limit[1];
		myPet.setIcon(picture[0]);//呈現寶可夢的圖片
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
	private class CandyHandler implements ActionListener{//按鈕GiveCandy所用的Handler，用於給寶可夢糖果
		public void actionPerformed(ActionEvent event){
			counter = counter+1;//糖果數加一
			if(counter==limit[0]&& level==0)//判斷糖果數是否達到進化為火恐龍的條件
			{
				JOptionPane.showMessageDialog(null, "進化成火恐龍");
				level = level+1;//等級加一
				monster = "火恐龍";//寶可夢種類變為火恐龍
				counter = 0;//糖果數歸零
			}
			else if(counter ==limit[1]&& level ==1){//判斷糖果數是否達到進化為噴火龍的條件
				JOptionPane.showMessageDialog(null, "進化成噴火龍，恭喜你成為寶可夢大師!");
				level = level+1;//等級加一
				monster = "噴火龍";//寶可夢種類變為噴火龍
			}
				candyNumber.setText(String.format("%d/%d",counter,limit[level]));//更新candyNumber所呈現的資訊
				myPet.setIcon(picture[level]);//更新寶可夢圖片
		}
	}
	private class DegenerateHandler implements ActionListener{//按鈕Degenerate的Handler，用於讓寶可夢退化
		public void actionPerformed(ActionEvent event){
			if(level!=0){//判斷寶可夢能否退化
				level = level-1;//等級減一
				JOptionPane.showMessageDialog(Pokemon.this, "寶可夢退化,糖果數歸零");
			}else{
				JOptionPane.showMessageDialog(Pokemon.this, "無法退化,糖果數歸零");
			}
			counter = 0;//糖果數歸零
			candyNumber.setText(String.format("%d/%d",counter,limit[level]));//更新candyNumber所呈現的資訊
			myPet.setIcon(picture[level]);//更新寶可夢圖片
				
		}
	}
	private class OpenGameHandler implements ActionListener{//按鈕OpenGame所用的Handler，用於開啟舊檔
		public void actionPerformed(ActionEvent event){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int result = fileChooser.showOpenDialog(Pokemon.this);
			if(result==JFileChooser.CANCEL_OPTION)
				System.exit(1);
			fileName = fileChooser.getSelectedFile();//將所選的檔案存入fileName
			filePath.setText(fileName.getAbsolutePath());//呈現檔案的絕對路徑
			try{
			obinput = new ObjectInputStream(new FileInputStream(fileName));//開啟舊檔
			record = (PokeSerializable) obinput.readObject();//強制轉換為PokeSerializable
			counter = record.getCandy();//將舊檔的糖果數存到counter
			monster= record.getMonster();//將舊檔的寶可夢種類存入monster
			nicknameField.setText(record.getNickname());//更新寶可夢暱稱
			
			switch(record.getMonster()){//判斷寶可夢種類，設定等級
			case "小火龍":
				level = 0;
				break;
			case "火恐龍":
				level = 1;
				break;
			case "噴火龍":
				level = 2;
				break;
			}
			
			candyNumber.setText(String.format("%d/%d",counter,limit[level]));//更新candyNumber所呈現的資訊
			myPet.setIcon(picture[level]);//更新寶可夢圖片
			
				input.close();
			}
			catch (IOException e) {
				System.err.println("讀檔案時發現錯誤");
			}
			catch(ClassNotFoundException classNotFoundException){
				System.err.println("無法建立物件");	
		    }	
			
		}
	}
	private class SaveAsHandler implements ActionListener{//按鈕SaveAs所用的Handler，用於另存新檔
		public void actionPerformed(ActionEvent event){
			PokeSerializable newRecord = new PokeSerializable(nicknameField.getText(),monster,counter);//create new record

			try{
				JFileChooser directoryChooser = new JFileChooser();
				directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = directoryChooser.showSaveDialog(Pokemon.this);
				if(result==JFileChooser.CANCEL_OPTION)
					System.exit(1);
				
				String newFileName = JOptionPane.showInputDialog(null,"請輸入檔名(需含副檔名)");
				File getDirectory = directoryChooser.getSelectedFile();
			
			//open file	
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(getDirectory.getAbsolutePath()+"/"+newFileName));
			output.writeObject(newRecord);//output record
			output.close();
			filePath.setText(getDirectory.getAbsolutePath()+"/"+newFileName);//更新filePath所呈現的檔案路徑
			
			}
			catch(IOException e) {
				System.err.println("存檔案時發現錯誤");
			}
		}
	}
	private class SaveHandler implements ActionListener{//按鈕Save所用的Handler，用於存檔
		public void actionPerformed(ActionEvent event){
			record = new PokeSerializable(nicknameField.getText(),monster,counter);
			
			try{
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
				output.writeObject(record);
				output.close();
				}
				catch(NullPointerException e){
					JOptionPane.showMessageDialog(Pokemon.this, "非舊檔，要存檔請按SaveAs");
					
				}
				catch(IOException e) {
					System.err.println("存檔案時發現錯誤");
				}
		}
	}
	
}
