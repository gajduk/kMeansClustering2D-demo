import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Aggregator {
	ArrayList<RectDouble> instances;
	HashMap<RectDouble,Integer> cluster_ids; 
	RectDouble clusters[];
	int k;
	
	public Aggregator(ArrayList<RectDouble> instances , int kk) {
		k = kk;
		cluster_ids = new HashMap<RectDouble,Integer>();
		clusters = new RectDouble[k];
		this.instances = new ArrayList<RectDouble>(instances.size());
		for ( RectDouble  instance : instances) this.instances.add(instance);
	}
	
	public void clusteringIterationFirstPart() {
		for ( RectDouble instance : instances ) {
			double min = instance.distance(clusters[0]);
			int min_idx = 0;
			for ( int i = 1 ; i < clusters.length ; ++i ) {
				double dist = instance.distance(clusters[i]);
				if ( dist < min ) {
					min = dist;
					min_idx = i;
				}
			}
			cluster_ids.put(instance, min_idx);
		}
	}
	
	public void clusteringIterationSecondPart() {
		int counts[] = new int[clusters.length];
		clusters = new RectDouble[counts.length];
		for ( int i = 0 ; i < clusters.length ; ++i ) {
			clusters[i] = new RectDouble();
		}
		for (Map.Entry<RectDouble,Integer> i : cluster_ids.entrySet()) {
			++counts[i.getValue()];
			clusters[i.getValue()] = clusters[i.getValue()].add(i.getKey());
		}
		for ( int i = 0 ; i < clusters.length ; ++i ) {
			clusters[i] = clusters[i].divide(counts[i]);
		}
	}
	
	public void init() {
		int group = (instances.size()-1)/k;
		clusters = new RectDouble[k];
		for ( int i = 0 ; i < k ; ++i ) {
			clusters[i] = instances.get(group*i+(int)(Math.random()*group));
		}
	}
}
