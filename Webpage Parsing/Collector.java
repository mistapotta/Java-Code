import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

class Student
{
	private String myName;
	private String mySchool;
	private int myScore;
	private String myDist;
	private String myPlace;
	
	public Student (String name, String school, int score, String dist, String place)
	{
		myName = name; mySchool=school; myScore=score;myDist = dist;myPlace=place;
	}
	
	public String getName() {return myName;}
	public String getSchool() {return mySchool;}
	public int getScore() {return myScore;}
	public String getDist() {return myDist;}
	public String getPlace() {return myPlace;}
}

public class Collector
{
	

	//parse together the URL for accessing the UIL website
	private int yr = 2013; // this is the current year
	private static String[] category = {"Calculator Applications", "Computer Science", "Mathematics", "Number Sense", "Science"};
	private static String one = "http://utdirect.utexas.edu/uil/vlcp_results.WBX?s_event_abbr="; //contestField
	private static String two = "&s_submit_sw=X&s_year=";
	private static String twohalf = "&s_conference="; //class
	private static String three = "A&s_level_id=D&s_level_nbr="; // district
	private static String four = "&s_gender=&s_round=&s_dept=C&s_meet_abbr=SPM&s_area_zone=";
	private static String[] contestField = {"CAL", "CSC", "MTH", "NUM", "SCI"};
	private String urlcontents,chosenClass;
	private int chosen,j;

	
	public Collector()
	{
		//declarations
		String filecontents="";
		String filename="";
//		for (yr = 2008; yr <= 2010; yr ++) 	//yr
		for (int clas=1; clas<=5; clas++)		//classification
		{
			for (int reg =1; reg <=32; reg++)	//district/region (use 1-4 for region)
			{
				//parse together the header of the HTML file
				filecontents = "<html><head><title>District "+reg+"-"+clas+"A</title></head><body BACKGROUND=\"/images/_bluelinebackground.png\" BGCOLOR=\"FFFFCC\" TEXT=\"333399\"><TABLE><TR><TD><PRE>";
				
				String div="District "+reg+"-"+yr; 	//comment this out for regionals and comment in the following:
//				for (int i = 0; i < reg; i++)
//					div += "I";
//				if (div.equals("IIII"))
//					div = "IV";
			
				div=div+"-";
				for (int i=0; i<clas; i++)
					div=div+"A";
				div+=" Results\n";
				filecontents+=div;
				filename = "d"+(reg<10?"0":"")+reg+"-"+clas+"a-"+yr+".html"; 	//uncomment this for multi year
				//filename = "d"+(reg<10?"0":"")+reg+"-"+clas+"a.html";				//uncomment this for single year

				for (int sub = 0; sub <= 4; sub++)
				{
					//go to each page and download the results
					String url =one + contestField[sub]+two+yr+twohalf+clas+three+reg+four;
						try
						{
							URL myURL = new URL(url);
							//System.out.println(url);	//for debugging
							URLConnection cc = myURL.openConnection();
							DataInputStream in = new DataInputStream(cc.getInputStream());
							int inputNums;
							while ((inputNums=in.read())!=-1)
							{
								urlcontents = urlcontents+(char)inputNums;
							}
							in.close();	
						}
						catch (Exception e)
						{
//							System.out.println("Failed for region "+reg+"-"+clas+"A.");	//for debugging
							e.printStackTrace();
						}
						//indicate we've successfully got the data, and write it to file.  
						System.out.println("Processing District "+reg+"-"+clas+"A - "+contestField[sub]);
						filecontents+=category[sub]+"\n";
						filecontents+=process(urlcontents);
						
						urlcontents="";
				}//for CAL/CSC/MTH/NUM/SCI
				
			
					try
					{	
						//add tail to the file and write it.
						filecontents+="</PRE></TD></TR></TABLE><TABLE><TR><TD><HR><A HREF=\"/_articles.html\">Articles</A> | <A HREF=\"/_calendar.html\">Calendar</A> | <A HREF=\"/forum\">Forum</A> | <A HREF=\"/_downloads.html\">Downloads</A> | <A HREF=\"/_purchases.html\">Purchases</A> | <A HREF=\"/_results.html\">Results</A><HR>Questions and comments should be directed to the <script><!--var f='<A HREF=\"mailto:webmaster@';var b='texasmath.org\">';document.write(f+b);// --></script>webmaster</A>.<HR></TD></TR></TABLE></body></html>";
						System.out.println(filecontents.length());
						System.out.println("Writing "+filename);	
						
						PrintWriter Fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
						int count;
						for(count = 0; count < filecontents.length()/65535; count++)
    						Fout.print(filecontents.substring(count*65535, count*65535+65535));     
						Fout.print(filecontents.substring(count*65535));
						//DataOutputStream Fout = new DataOutputStream(new FileOutputStream(filename));
						//int count;
						//System.out.println(Fout.size()-filecontents.getBytes().length);
						//for( count=0; count<filecontents.length()/65535; count++)
						//	Fout.writeUTF(filecontents.substring(count*65535, count*65535+65535));		
						//Fout.writeUTF(filecontents.substring(count*65535));
						Fout.flush();
						Fout.close();
						filecontents="";
			
			
			
					}//try
					catch (Exception e)
					{
						System.out.println("Failed writing "+filename);
						e.printStackTrace();
					}//catch
			}//for district 1-32/region 1-4
		}//for class 1A-5A
		
	}	
		
		


	//used for debugging
	public void sift(StringTokenizer st, int count)
	{
		for (int i = 0; i < count; i++) {System.out.println(""+i+" "+st.nextToken());}
	}
	
	//skip the appropriate number of steps to find the data
	public void pause(StringTokenizer st)
	{
		String foo="";
		while (st.hasMoreTokens() && !foo.equals("td  class=\"fineprnt\" nowrap=\"nowrap\""))
		{
			if (st.hasMoreTokens())
			foo = st.nextToken();
			else break;
		}
	}
	
	//another skipper
	public void ppause(StringTokenizer st)
	{
		String foo="";
		while (st.hasMoreTokens() && !foo.equals("td  class=\"fineprnt\" nowrap=\"nowrap\""))
		{	if (st.hasMoreTokens())
			foo = st.nextToken();
			else break;
		}
	}
	
	//a third skipper
	public void qpause(StringTokenizer st)
	{
		String foo="";
		while (st.hasMoreTokens() && !foo.equals("td  class=\"fineprnt\" style=\"text-align:right;\" nowrap=\"nowrap\""))
		{
			if (st.hasMoreTokens())
			foo = st.nextToken();
			else break;
		}
	}
	
	//get the numerical score (decimal in case of tie breaker)
	public int scoreit(String s)
	{
		while (s.charAt(0)<'0')
			s=s.substring(1);
		return (int)(Double.parseDouble(s));
	}
	
	//process the string by getting at least the top six individuals and the top two teams
	public String process(String urlContents)
	{
		//two tokenizers -- one for indiv and one for team
		StringTokenizer st = new StringTokenizer(urlContents,"<>");
		StringTokenizer st2 = new StringTokenizer(urlContents,"<>");
		//sift(st,500); //debugging
		String filecontents = "";
		String foo="";
		String name="",school="",score="",place="";
		//go down to individual results in st
		while (st.hasMoreTokens() && !foo.equals("Individual Results"))
		{
			if (st.hasMoreTokens()) foo = st.nextToken(); else break;
		}
		//and go down to team for st2
		while (st.hasMoreTokens() && !foo.equals("Team Results"))
		{
			if (st2.hasMoreTokens())foo = st2.nextToken(); else break;
		}
		//parse the html for student name, school name, score, and place (in case of ties)
		while (st.hasMoreTokens())
		{
			pause(st);
			if (st.hasMoreTokens())name=st.nextToken();else break;
//			System.out.println(name);	//debugging
			int comma = name.indexOf(",");
			//last, first to first last name
			if (comma>-1 && name.length() > comma + 2)
				name = name.substring(comma+2)+" "+name.substring(0,comma);
			else
				break;
			pause(st);	//jump to school
			if (st.hasMoreTokens())	school = st.nextToken(); else break;
//			System.out.println(school);	//debugging
			qpause(st);	//jump to score
			if (st.hasMoreTokens())score = st.nextToken(); else break;
//			System.out.println(score);	//debugging
			int stuscore = scoreit(score);
			qpause(st);	//jump to place
			if (st.hasMoreTokens())  place = st.nextToken();else break;
//			System.out.println(place);	//debugging
//			qpause(st);	//debugging
//			qpause(st);	//debugging
			if (st.hasMoreTokens())foo = st.nextToken();else break;
			//stop if less than five students
			if (school.equals("1") || scoreit(place) > 6) break;
			//add it to the output
			filecontents+=place+" - "+name+", "+school+", "+(stuscore)+"<BR>";
		}
		filecontents+="\nTeam:\n";
		while (st2.hasMoreTokens())
		{
//			sift(st2,210);	//debugging
			pause(st2);
			if (st2.hasMoreTokens()) name=st2.nextToken(); else break;
			//schools should be longer than for chars
			if (name.length()<4)
				break;
//			System.out.println(name);	//debugging
			pause(st2);
			if (st2.hasMoreTokens())school = st2.nextToken(); else break;
//			System.out.println(school);	//debugging
			qpause(st2);
			if (st2.hasMoreTokens())score = st2.nextToken(); else break;
//			System.out.println(score);	//debugging
			int stuscore = scoreit(score);
			qpause(st2);
			//if (st2.hasMoreTokens()) pause(st2); else break;
			if (st2.hasMoreTokens()) place = st2.nextToken();else break;
//			System.out.println(place);
			//if (st2.hasMoreTokens()) pause(st2);else break;
			//if (st2.hasMoreTokens()) qpause(st2);else break;
			//if (st2.hasMoreTokens()) qpause(st2);else break;
			//if (st2.hasMoreTokens()) foo = st2.nextToken();
			if (scoreit(place) > 2) break;
			filecontents+=place+" - "+name+", "+(stuscore)+"<BR>";
		}

		return filecontents+"\n<P>";
	}
	
	//the shizzle	
	public static void main (String a[])
	{
		new Collector();
		System.exit(0);
	}
}
