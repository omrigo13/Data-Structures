
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Flight {
	//submitted by Omri Goldberg and Assaf Shookroon
	
	
	/**
     * calculates the amount of passengers in a file
     * @return Integer represents amount of passengers
     * @param String passengers file with full details
     */
	public static int countLines(String path) {
		int count = 0;
		try {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String next;
		while((next = reader.readLine()) != null) {
			count++;
		}
		reader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + path + "'");
		}
		catch (IOException e) {
			System.out.println("Error reading file '" + path + "'");
		}
		return count;
		}
	
	/**
     * makes a new HashTable from a file of passengers
     * @return HashTable of passengers
     * @param String passengers file with full details
     */
	public static HashTable add(String path){
		HashTable lines = new HashTable(countLines(path));
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			String[] data;
			while((next = reader.readLine()) != null) {
				data = next.split(",");
				lines.add(new Node(Integer.valueOf(data[0]),data[1],data[2],data[3]));
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + path + "'");
		}
		catch (IOException e) {
			System.out.println("Error reading file '" + path + "'");
		}
		return lines;
	}
	
	/**
     * calculates the average to find all passengers on HashTable
     * @return Integer average moves calculation
     * @param HashTable hash - passengers who registered to Flight
     * @param String path - passengers who tries to check in
     */
	public static int moves(HashTable hash, String path){
		int avg = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String next;
			String[] data;
			Node node;
			while((next = reader.readLine()) != null) {
				data = next.split(",");
				node = new Node(Integer.valueOf(data[0]),data[1],data[2],data[3]);
				avg += hash.moves(node);
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + path + "'");
		}
		catch (IOException e) {
			System.out.println("Error reading file '" + path + "'");
		}
		return avg/countLines(path);
	}
	
	/**
     * checks how much pessengers can be added to flight and adds them
     * @return ArrayList<Integer> Ids list of passengers who checked in successfully to flight and are waiting to sit
     * @param String OnFlight - passengers who registered to Flight
     * @param String AddToFlight - passengers who tries to check in
     */
	public static ArrayList<Integer> CanAdd(String OnFlight , String AddToFlight){
		int count = 0;
		HashTable Flight = add(OnFlight);
		ArrayList<Node> AddFlight = new ArrayList<>();
		ArrayList<Integer> Ids = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(AddToFlight));
			String next;
			String[] data;
			Node node;
			while((next = reader.readLine()) != null) {
				data = next.split(",");
				node = new Node(Integer.valueOf(data[0]),data[1],data[2],data[3]);
				if(Flight.register(node) == true) {
					count++;
					Ids.add(node.getId());
				}
				else
					AddFlight.add(node);
			}
			Collections.sort(AddFlight);
			int min = AddFlight.size();
			if((countLines(OnFlight)-count) < min) min = (countLines(OnFlight)-count);
			for(int i = 0; i < min; i++)
				Ids.add(AddFlight.get(i).getId());
			Collections.sort(Ids);
			reader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to open file/s");
		}
		catch (IOException e) {
			System.out.println("Error reading file/s");
		}
		return Ids;
	}
	
	/**
     * converts Ids integer list to a long string of Ids separated by ','
     * @return String Ids of passengers
     * @param ArrayList<Integer> Ids - passengers Ids list of passengers
     */
	public static String IdsListToStr(ArrayList<Integer> Ids) {
		String AddId = "";
		for(int i = 0; i<Ids.size(); i++) {
			if(i == Ids.size() - 1)
				AddId+=Ids.get(i);
			else
				AddId+=Ids.get(i)+",";
		}
		return AddId;
	}

	/**
     * calculates moves to put all passengers between start and end
     * @return Integer moves calculation to put passengers on flight
     * @param int start - first passenger to put on Flight
     * @param int end - last passenger to put on Flight
     * @param ArrayList<Integer> Ids - passengers Ids list of passengers to put on Flight
     */
	public static int insert(int size ,ArrayList<Integer> Ids , int start, int end) {
		int[] Airplane = new int[size];
		int index;
		int count = 0;
		for(int m = start; m < end; m++) {
			index = Ids.get(m)%size;
			if(Airplane[index] == 0)
				Airplane[index] = Ids.get(m);
			else {
				for(int i = 1; i < Airplane.length;i++)
				{
					if(index + i < Airplane.length)
					{
						if (Airplane[index+i] == 0)
						{
							Airplane[index+i] = Ids.get(m);
							count++;
							break;
						}
						else
							count++;
					}
					 if(index - i >= 0)
					{
						if (Airplane[index - i] == 0)
							
						{
							Airplane[index-i] = Ids.get(m);
							count++;
							break;
						}
						else
							count++;

					}
				}
			}
		}
		return count;
	}
	
	/**
     * calculates moves to put passengers on flight
     * @return String moves calculation to first N/2 passengers, 3N/4 first passengers, N - sqrt(N) first passengers, sqrt(N) last passengers
     * @param String OnFlight - passengers who registered to Flight
     * @param String AddToFlight - passengers who checked in to Flight
     * @param ArrayList<Integer> Ids - passengers Ids list of passengers to put on Flight
     */
	public static String HashFunc(String OnFlight , String AddToFlight, ArrayList<Integer> Ids) {
		return insert(countLines(OnFlight),Ids,0,Math.min(countLines(OnFlight), CanAdd(OnFlight, AddToFlight).size())/2) + "," + insert(countLines(OnFlight),Ids,0,(Math.min(countLines(OnFlight), CanAdd(OnFlight, AddToFlight).size())*3)/4) + "," + 
				insert(countLines(OnFlight),Ids,0,Math.min(countLines(OnFlight), CanAdd(OnFlight, AddToFlight).size())-(int)Math.sqrt(Math.min(countLines(OnFlight), CanAdd(OnFlight, AddToFlight).size()))) + "," + 
				(insert(countLines(OnFlight),Ids,0,Math.min(countLines(OnFlight), CanAdd(OnFlight, AddToFlight).size())) - insert(countLines(OnFlight),Ids,0,Math.min(countLines(OnFlight), CanAdd(OnFlight, AddToFlight).size())-(int)Math.sqrt(Math.min(countLines(OnFlight), CanAdd(OnFlight, AddToFlight).size()))));
	}
	
	/**
     * writes all the calculations of the functions to a file
     * @param String path1 - represents the passengers who registered to the Flight
     * @param String path2 - represents the passengers who tried to check in to the Flight
     * @param String path3 - represents the output file to write all the details at the end
     */
	public static void writeToFile(String path1, String path2, String path3) {
		HashTable data = add(path1);
		try {
			PrintWriter writer = new PrintWriter(path3);
			for(int i = 0; i<data.getSize(); i++) {
				if(i == data.getSize() - 1)
					writer.println(data.getHash()[i].getSize());
				else
					writer.print(data.getHash()[i].getSize()+",");
			}
			writer.println(moves(data,path2));
			writer.println(IdsListToStr(CanAdd(path1, path2)));
			writer.println(HashFunc(path1, path2, CanAdd(path1, path2)));
			ArrayList<Integer> Ids2 = CanAdd(path1, path2);
			ArrayList<Integer> Ids = new ArrayList<>();
			for(int i = 0; i < Ids2.size(); i++) {
				Ids.add(ReverseDigits(Ids2.get(i)));
			}
			writer.println(HashFunc(path1, path2, Ids));
			writer.close();
		}
		catch (IOException e) {
			System.err.println(e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	
	/**
     * calculates the reverse Id number of a passenger
     * @return Integer reversed passenger Id number
     * @param Integer passenger Id number
     */
	public static int ReverseDigits(int num) {
		int reversed = 0;
		while(num != 0) {
			int digit = num % 10;
			reversed = reversed * 10 + digit;
			num = num / 10;
		}
		return reversed;
	}
	
	public static void main(String[] args) {
		writeToFile(args[0], args[1], args[2]);
	}
}
