package command_line;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GUI {
		/*
		 * @Description:		windows for commands
		 * @author:					Judiths01
		 * @time:					  2014-10-29
		 */
		public void welcome(){
			System.out.println("==========欢迎使用命令行==========");
			Scanner s = new Scanner(System.in);						
			String command = null;
			try{				
				System.out.print("请输入您的命令:\t");
				command = s.nextLine();
				
				while(!command.equals("quit")){						//terminal
					if(command.startsWith("dir"))									        
					{	
						try{
							dodir(command);								
						}catch(Exception e){
							e.printStackTrace();
						}
					}else if(command.startsWith("cat")){			
						try{
							docat(command);
						}catch(Exception e){
							if(e instanceof FileNotFoundException){
								System.out.println("文件没有找到!");
							}else if(e instanceof NullPointerException){
								System.out.println("参数个数有错误！");
							}else{
								e.printStackTrace();
							}
						}
					}else if(command.startsWith("gen")){						
						dogen(command);
						
					}else if(command.startsWith("mv")){
						domv(command);
						
					}else if(command.startsWith("cp1")){
						try{
							docp1(command);
						}catch(Exception e){
							if(e instanceof FileNotFoundException){
								System.out.println("文件不存在!");
							}else{
								e.printStackTrace();
							}
						}
						
					}else if(command.startsWith("cp2")){
						docp2(command);
						
					}else if(command.startsWith("enc")){
						doenc(command);
						
					}else if(command.startsWith("dec")){
						dodec(command);
						
					}
					System.out.print("请输入命令: ");
					command = s.nextLine();
				}
				s.close();
			}catch(Exception e){
				System.out.println("请检查您的输入命令,您可以通过 '-help'获得帮助");
				System.out.println("==Man for help=="); 				//作输入提示
				command = s.nextLine();
			}
		}
	/*
 	* @Description:		返回当前目录下的路径
 	* @parameters:		command(String )
 	* @return:			void
 	* */
	private void dodir(String command) throws IOException  {
			
			String[] args = command.split(" ");
			System.out.println(args);
			FileOperation fo = new FileOperation();
			List<String> list = fo.dir(args[1]);
			for(String s:list)
				System.out.println(s);
		}
	/*
	 *@Description:		"捕获“目标目录下的文件，并将目标文件的内容显示出来
	 *@parameter:		  command(String )
	 *@return:			void
	 **/	
	
	private void docat(String command) throws IOException{
		String[] args = command.split(" ");
		FileOperation fo = new FileOperation();
		System.out.println(fo.cat(args[1]));
	}

	
	/*
	 * @Description:		在指定目录下创建目标文件
	 * @parameter:			command(String )
	 * @return:				void
	 * */
	private void dogen(String command){
		String[] args = command.split(" ");
		FileOperation fo = new FileOperation();
		System.out.println(fo.genFile(args[1]));
	}
	/*
	 * @Description:			删除指定目录下的文件
	 * @parameter:				command(String)
	 * @return:					void
	 * */
	private void domv(String command){
		String[] args = command.split(" ");
		FileOperation fo = new FileOperation();
		fo.mvFile(args[1]);
	}
	
	private void docp1(String command) throws IOException{
		String[] args = command.split(" ");
		FileOperation fo = new FileOperation();
		fo.cpFolder(args[1], args[2]);
		//		File s = new File(args[1]);
//		File t = new File(args[2]);
//		long start, end;
//		start = System.currentTimeMillis();
//		end = System.currentTimeMillis();
	}
	private void docp2(String command){
		String[] args = command.split(" ");
		FileOperation fo = new FileOperation();
		fo.cpFolder(args[1], args[2]);	
	}
	/*
	 * */
	private void doenc(String command) throws IOException{
		String[] args = command.split(" ");
		FileOperation fo = new FileOperation();
		fo.enCodeFile(args[1], args[2]);
	}
	/*
	 * */
	private void dodec(String command){
		String[] args = command.split(" ");
		FileOperation fo = new FileOperation();
		fo.deCodeFile(args[1], args[2]);
	}
	
	public static void main(String[] arg){
			UIx ui = new UIx();
			ui.welcome();
		}
}

