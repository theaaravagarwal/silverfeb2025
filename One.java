import java.io.*;
import java.util.*;
public class One {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int T = Integer.parseInt(br.readLine().trim());
        for (int t=0; t<T; t++) {
            int n = Integer.parseInt(br.readLine().trim());
            String[] parts = br.readLine().split(" ");
            int[] FjFavCows = new int[n];
            for (int i=0; i<n; i++) FjFavCows[i] = Integer.parseInt(parts[i]);
            boolean nonIncreasing = true;
            for (int i=0; i<n-1; i++) if (FjFavCows[i]<FjFavCows[i+1]) { nonIncreasing = false; break; }
            int[] bestB = comp(FjFavCows);
            int[] bestCandidateB = bestB;
            if (!nonIncreasing) {
                ArrayList<Integer> records = new ArrayList<>();
                int maxSoFar = -1;
                for (int i=n-1; i>=0; i--) if (FjFavCows[i]>=maxSoFar) { records.add(i); maxSoFar = FjFavCows[i]; }
                Collections.reverse(records);
                if (!records.isEmpty()&&records.get(0)>0) {
                    int m = FjFavCows[records.get(0)]; 
                    int lastOccurrence = records.get(0);
                    for (int i=0; i<n; i++) if (FjFavCows[i]==m) lastOccurrence = i;
                    if (lastOccurrence>0) {
                        int[] candidateArray = sim(FjFavCows, lastOccurrence, 0);
                        int[] candidateB = comp(candidateArray);
                        if (lcomp(candidateB, bestCandidateB)>0) bestCandidateB = candidateB;
                    }
                }
                for (int j=1; j<records.size(); j++) {
                    int fromIndex = records.get(j);
                    int toIndex = records.get(j-1)+1;
                    if (fromIndex > toIndex) {
                        int[] candidateArray = sim(FjFavCows, fromIndex, toIndex);
                        int[] candidateB = comp(candidateArray);
                        if (lcomp(candidateB, bestCandidateB)>0) bestCandidateB = candidateB;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<bestCandidateB.length; i++) {
                sb.append(bestCandidateB[i]);
                if (i<bestCandidateB.length-1) sb.append(" ");
            }
            out.println(sb.toString());
        }
        out.flush();
        out.close();
    }
    static int[] comp(int[] arr) {
        int n = arr.length;
        int[] suffixMax = new int[n];
        suffixMax[n-1] = arr[n-1];
        for (int i=n-2; i>=0; i--) suffixMax[i] = Math.max(arr[i], suffixMax[i+1]);
        ArrayList<Integer> rec = new ArrayList<>();
        for (int i=0; i<n; i++) if (arr[i]==suffixMax[i]) rec.add(arr[i]);
        int[] res = new int[rec.size()];
        for (int i=0; i<rec.size(); i++) res[i] = rec.get(i);
        return res;
    }
    static int[] sim(int[] arr, int fromIndex, int toIndex) {
        int n = arr.length;
        int[] res = new int[n];
        if (toIndex > 0) System.arraycopy(arr, 0, res, 0, toIndex);
        res[toIndex] = arr[fromIndex];
        if (fromIndex - toIndex > 0) System.arraycopy(arr, toIndex, res, toIndex+1, fromIndex - toIndex);
        if (n-fromIndex-1>0) System.arraycopy(arr, fromIndex+1, res, fromIndex+1, n-fromIndex-1);
        return res;
    }
    static int lcomp(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);
        for (int i=0; i<len; i++) if (a[i]!=b[i]) return a[i]-b[i];
        return a.length-b.length;
    }
}