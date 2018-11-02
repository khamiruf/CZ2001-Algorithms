import java.util.*;
public class Main {

	public static void BFS(int source, int destination, LinkedList<Integer>[] adjacentCity, Stack<Integer> way,int numOfCity)
	{
		List<Boolean> visited = new ArrayList<>();
		List<Integer> prev = new ArrayList<>();
		
		for (int i=0;i<numOfCity;i++)
		{	
			visited.add(false);
			prev.add(numOfCity);
		}
		
		visited.set(source,true);
		
		LinkedList<Integer> queue=new LinkedList<>();
		queue.add(source);
		
		int curr=source;
		
		while (!queue.isEmpty() && curr!=destination)
		{
			curr=queue.poll();
			LinkedList<Integer> adj=adjacentCity[curr];
			for (Integer i:adj)
			{
				if (visited.get(i)== false)
				{
					queue.add(i);
					visited.set(i,true);
					prev.set(i, curr);
				}				
			}
			
		}
		int prevLoc=destination;
		while (prevLoc!=source)
		{
			way.push(prevLoc);
			prevLoc=prev.get(prevLoc);
		}
		way.push(source);
	}
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		while (true)
		{
			System.out.println("Enter the number of cities:");
			int numOfCity = sc.nextInt();
			LinkedList<Integer>[] adjacentCity= new LinkedList[numOfCity];
			for (int i=0;i<numOfCity;i++)
			{
				LinkedList<Integer> adjacent= new LinkedList<Integer>();
				adjacentCity[i]=adjacent;
			}
			String[]cityName=CityNameGenerator.generateCityNames("src/cities_1000.txt",numOfCity);
			
			int numOfEdge;
			do {
				//conditions for the min and max number of edges, based on the number of nodes(numOfCity)
				System.out.println("Enter the number of edge("+(numOfCity-1)+"-"+(((numOfCity*(numOfCity-1)/2))-1)+")\t:");
				numOfEdge=sc.nextInt();
			} while (numOfEdge<(numOfCity-1) || numOfEdge>(numOfCity)*((numOfCity-1/2)-1));

			Random random = new Random();

			//create minimumEdges for BST
			for (int i=0;i<numOfCity;i++)
			{
				if (i>0)
				{
					adjacentCity[i].add(i-1);
					adjacentCity[i].add(i);
				}
			}

			for (int i=0;i<numOfEdge-(numOfCity-1);i++)
			{
				int v1=random.nextInt(numOfCity);
				int v2=random.nextInt(numOfCity);

				//if the vertices index are the same, or only differs by 1, or has already had edges between them, continue looping
				if (Math.abs((v1-v2))<=1|| adjacentCity[v1].contains(v2))
					i--;

				//otherwise, add it into the graph
				else
				{
					adjacentCity[v1].add(v2);
					adjacentCity[v2].add(v1);
				}
			}
			for (int i=0;i<numOfCity;i++)
			{
				System.out.println("["+i+"]\t"+cityName[i]);
				System.out.println(i+"\t"+adjacentCity[i]);
			}

			int source=-1,destination=-1;
			do {
				System.out.println("Enter source ID:"); 
				source = sc.nextInt();
				System.out.println("Enter destination ID :");
				destination = sc.nextInt();
			} while (source<0 ||source>=numOfCity || destination<0 || destination>=numOfCity );

			List<Integer> wayToGo = new ArrayList<>();
			Stack<Integer> way = new Stack<Integer>();
			
			long start=System.nanoTime();
			BFS(source,destination,adjacentCity,way,numOfCity);
			long end=System.nanoTime();
			
			while (!way.isEmpty())
			{
				int curr=way.pop();
				System.out.println(curr+" "+cityName[curr]);
			}
			System.out.println("Time taken\t:"+ (end-start));
		
			System.out.println("Do you want to continue?Y/N");
			String input=sc.next();
			if (input.equals("N"))
				break;
		}
	}

}
