import java.io.*;
import java.util.*;
public class Two {
    static int n;
    static int m;
    static int[] par;
    static int[] depth;
    static int[][] up;
    static int[] head;
    static int[] nxt;
    static int[] child;
    static int[] leafOrder;
    static int[] leafId;
    static int leafCount = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        par = new int[n+1];
        depth = new int[n+1];
        par[0] = -1;
        depth[0] = 0;
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<n+1; i++) {
            par[i] = Integer.parseInt(st.nextToken());
            depth[i] = depth[par[i]]+1;
        }
        head = new int[n+1];
        Arrays.fill(head, -1);
        nxt = new int[n+1];
        child = new int[n+1];
        for (int i=1; i<n+1; i++) {
            child[i] = i;
            nxt[i] = head[ par[i] ];
            head[ par[i] ] = i;
        }
        for (int i=0; i<n+1; i++) {
            head[i] = reverseList(head[i]);
        }
        leafOrder = new int[n+1];
        leafId = new int[n+1];
        dfs(0);
        m = leafCount;
        int maxPow = 32-Integer.numberOfLeadingZeros(n+1);
        up = new int[maxPow][n+1];
        for (int i=0; i<n+1; i++) {
            up[0][i] = par[i];
        }
        for (int i=1; i<maxPow; i++) {
            for (int v=0; v<n+1; v++) {
                int ancestor = up[i-1][v];
                up[i][v] = (ancestor==-1?-1:up[i-1][ancestor]);
            }
        }
        Bit bit = new Bit(m);
        for (int i=1; i<=m; i++) bit.update(i, 1);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        for (int i=0; i<m; i++) {
            int node = Integer.parseInt(br.readLine());
            int order = leafOrder[node];
            if(bit.sum(m)==1) out.println(0);
            else {
                int best = 0;
                if(order>1&&bit.sum(order-1)>0) {
                    int predIndex = bit.find(bit.sum(order-1));
                    int predNode = leafId[predIndex];
                    int lcp = lcaDepth(node, predNode);
                    if(lcp>best) best = lcp;
                }
                int totalActive = bit.sum(m);
                if(bit.sum(order)<totalActive) {
                    int succIndex = bit.find(bit.sum(order)+1);
                    int succNode = leafId[succIndex];
                    int lcp = lcaDepth(node, succNode);
                    if(lcp>best) best = lcp;
                }
                out.println(best + 1);
            }
            bit.update(order, -1);
        }
        out.flush();
        out.close();
    }
    static int reverseList(int headIndex) {
        int prev = -1;
        int curr = headIndex;
        while(curr != -1) {
            int nextVal = nxt[curr];
            nxt[curr] = prev;
            prev = curr;
            curr = nextVal;
        }
        return prev;
    }
    static void dfs(int root) {
        int totalNodes = depth.length;
        int[] stack = new int[totalNodes];
        int[] ptr = new int[totalNodes];
        int sp = 0;
        stack[sp] = root;
        ptr[sp] = head[root];
        sp++;        
        while(sp>0) {
            int node = stack[sp-1];
            int childEdge = ptr[sp-1];
            if(childEdge==-1) {
                if(head[node]==-1) {
                    leafCount++;
                    leafOrder[node] = leafCount;
                    leafId[leafCount] = node;
                }
                sp--;
                if(sp>0) ptr[sp-1] = nxt[ptr[sp-1]];
            } else {
                stack[sp] = child[childEdge]; 
                ptr[sp] = head[stack[sp]];
                sp++;
            }
        }
    }
    static int lcaDepth(int u, int v) {
        if(depth[u]<depth[v]) {
            int temp = u; u = v; v = temp;
        }
        int d = depth[u]-depth[v];
        int level = 0;
        while(d>0) {
            if((d&1)!=0) u = up[level][u];
            d>>=1;
            level++;
        }
        if(u==v) return depth[u];
        for(int i=up.length-1; i>=0; i--) if (up[i][u]!=up[i][v]) {
            u = up[i][u];
            v = up[i][v];
        }
        return depth[ par[u] ];
    }
    static class Bit {
        int n;
        int[] tree;
        public Bit(int n) {
            this.n = n;
            tree = new int[n+1];
        }
        public void update(int i, int delta) {
            for(; i<=n; i+=i&-i) tree[i]+=delta;
        }
        public int sum(int i) {
            int s = 0;
            for(; i>0; i-=i&-i)
                s+=tree[i];
            return s;
        }
        public int find(int k) {
            int idx = 0;
            int bitMask = Integer.highestOneBit(n);
            while(bitMask != 0) {
                int nextIdx = idx + bitMask;
                if(nextIdx <= n && tree[nextIdx] < k) {
                    k -= tree[nextIdx];
                    idx = nextIdx;
                }
                bitMask >>= 1;
            }
            return idx + 1;
        }
    }
}