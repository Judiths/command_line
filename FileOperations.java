package command_line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileOperation {
	
	/*
	 * @Description:		进入指定目录下的文件夹，并将其下的文件及文件夹按自定义的格式输出
	 * @time:			2014-10-31
	 * @author:			Judiths01
	 * @parameter:		dir
	 * @return:			list(List<String>)
	 * */ 	
	public List<String> dir(String dir) throws IOException {
		List<String> list = new ArrayList<String>();
		File file = new File(dir);
		for(File f:file.listFiles()){
			StringBuffer buffer = new StringBuffer();
			if(f.isDirectory()){
				buffer.append("d");
			}else{
				buffer.append("-");
			}
			if(f.canRead()){
				buffer.append("r");
			}else{
				buffer.append("-");
			}
			if(f.canWrite()){
				buffer.append("w");
			}else{
				buffer.append("-");
			}
			if(f.canExecute()){
				buffer.append("x");
			}else{
				buffer.append("-");
			}
			if(f.isDirectory()){
				String[] files = f.list();
				buffer.append(files != null ? files.length:0);
			}else{
				buffer.append(1);
			}
			buffer.append("\t");
			buffer.append(f.getTotalSpace());
			buffer.append("\t");
			Date d = new Date(f.lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			buffer.append(sdf.format(d));
			buffer.append("\t");
			buffer.append(f.getName());
			System.out.println(f.getName());
			list.add(buffer.toString());	
		}
		return list;
	}
	/*
	 * @Description:	实现指定目录下文件内容的罗列
	 * @time:			2014-10-31
	 * @author:			Judiths01
	 * @parameter:		cat
	 * @return:			String
	 * */ 	
	public String cat(String filename) throws IOException{
		StringBuffer buffer = new StringBuffer();	
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = reader.readLine();
		while(line != null){
			buffer.append(line);
			buffer.append("\n");
			line = reader.readLine();
		}
		reader.close();
    	return buffer.toString();	
	}
	/*
	 * @Description:		创建新的文件
	 * @time:			2014-10-31
	 * @author:			Judiths01
	 * @parameter:		filename
	 * @return:			新文件所在的绝对路径*/
	public String genFile(String filename){
		File f = new File(filename);
		if(f.exists()){
			System.out.println("该文件已存在！");
		}else{
			f.mkdirs();
		}
		return f.getAbsolutePath();						//
	}
	
	/*
	 * @Description:		删除已存在的文件
	 * @time:				2014-10-31
	 * @author:				Judiths01
	 * @parameter:			filename
	 * @return:				void(null)
	 * */
	 
	public void mvFile(String filename){
		File f = new File(filename);
		if(!f.exists()){
			System.out.println("该文件不存在！");
		}else{
			f.delete();
		}
	}
	/*
	 * @Description:		实现文件的拷贝
	 * @author:				Judiths01
	 * @time:				2014-10-31
	 * @parameters:			source_file(String), destination_filename(String)
	 * @return:				null
	 * */
	/*public String cpFile(String src_filename,String des_filename) throws IOException{
		File src_f = new File(src_filename);
		if(!src_f.exists()){
			System.out.println("该文件不存在！");
//			System.exit(0);
		}
		File des_f = new File(des_filename);
		if(!des_f.exists()){
			des_f.mkdirs();
		}
		Scanner input = new Scanner(src_filename);
		PrintWriter output = new PrintWriter(des_filename);
		while(input.hasNext()){
			String s1 = input.nextLine();
			String s2 = s1;
			output.print(s2);
		}
		input.close();
		output.close();
		return des_f.getAbsolutePath();
	}*/
	
	/*
	 * @Description:		实现文件的拷贝
	 * @author:				Judiths01
	 * @time:				2014-10-31
	 * @parameters:			s(File), d(File)
	 * @return:				null
	 * */
	public void cpFile(File s, File d) throws FileNotFoundException {
        FileInputStream fi = new FileInputStream(s);
        FileOutputStream fo = new FileOutputStream(d);
        FileChannel in = null;
        FileChannel out = null;
        try {
 
        	in = fi.getChannel();
        	out = fo.getChannel();
            in.transferTo(0, in.size(), out);
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            
        	try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	/*
	 * @Description:		实现文件夹的拷贝(深拷贝)
	 * @author:				Judiths01
	 * @time:				2014-10-31
	 * @parameters:			src_folder(String), des_folder(String)
	 * @return:				null
	 * */
	public void cpFolder(String src_folder, String des_folder){
		try{
			File desFile = new File(des_folder);
			File srcFile = new File(src_folder);
			String[] file = srcFile.list();
			File tmpFile = null;

			desFile.mkdirs();
			for(int i = 0; i<file.length; i++){
				if(src_folder.endsWith(File.separator)){
					tmpFile = new File(src_folder + file[i]);
				}else{
					tmpFile = new File(src_folder + File.separator + file[i]);
				}
					
				if(tmpFile.isFile()){
					cpFile(tmpFile, desFile);
				}else if(tmpFile.isDirectory()){
					cpFolder(src_folder + "/" + file[i], des_folder + "/" +file[i]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * @Description:		实现指定的文件加密
	 * @author:				Judiths01
	 * @time:				2014-10-31
	 * @parameters:			ori_filename(String), encodeFile(String)
	 * @return:				null
	 * */
	public void enCodeFile(String ori_filename,String encodefilename) throws IOException{
		File ori_file = new File(ori_filename);
		File enc_file = new File(encodefilename);
		
		try{
			if(!ori_file.exists()){
				System.out.println("你希望加密的文件不存在！");
			}else{
				Scanner input = new Scanner(ori_file);
				PrintWriter output = new PrintWriter(enc_file);
				while(input.hasNext()){
					String s1 = input.next();
					String s2 = s1.replace("o", "e");
					output.println(s2);
				}
				input.close();
				output.close();
				
			}
		}catch(Exception e){
			
		}
		
	}
	/*
	 * @Description:		实现指定的文件的解密
	 * @author:				Judiths01
	 * @time:				2014-10-31
	 * @parameters:			encodedFile(String), ori_filename(String)
	 * @return:				null
	 * */
	public void deCodeFile(String encodedFile, String ori_filename){
		File encd_file = new File(encodedFile);
		File ori_file = new File(ori_filename);
		
		try{
			if(!encd_file.exists()){
				System.out.println("您希望解密的文件不存在！");
			}else{
				Scanner input = new Scanner(encd_file);
				PrintWriter output = new PrintWriter(ori_file);
				while(input.hasNext()){
					String s1 = input.next();
					String s2 = s1.replace("e", "o");
					
					output.println(s2);
				}
				input.close();
				output.close();
				
			}
		}catch(Exception e){
			
		}
	}
	/*
	public static void main(String[] args) throws IOException{
		FileOperation foo = new FileOperation();
		List<String> list = foo.dir("/");
		for(String s : list)
			System.out.println(s);
	*/		
}
