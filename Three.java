import java.io.*;
public class Three {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int t=0; t<T; t++) {
            String[] parts = br.readLine().split(" ");
            long a = Long.parseLong(parts[0]), b = Long.parseLong(parts[1]);
            long c = Long.parseLong(parts[2]), d = Long.parseLong(parts[3]);
            if (a==c&&b==d) {
                System.out.println(0);
                continue;
            }
            long ab = a+b, cd = c+d;
            if (ab>cd) {
                System.out.println(-1);
                continue;
            }
            if (ab==cd) {
                System.out.println(-1);
                continue;
            }
            long x = c, y = d;
            long cnt = 0;
            while (true) {
                if (x==a&&y==b) {
                    System.out.println(cnt);
                    break;
                }
                if (x<a||y<b) {
                    System.out.println(-1);
                    break;
                }
                if (x==y) {
                    System.out.println(-1);
                    break;
                }
                if (x>y) {
                    if (y<b) {
                        System.out.println(-1);
                        break;
                    }
                    if (y==b) {
                        long diff = x-a;
                        if (diff<0) {
                            System.out.println(-1);
                            break;
                        }
                        if (diff%y!=0) {
                            System.out.println(-1);
                            break;
                        }
                        cnt += diff/y;
                        x = a;
                        if (x==a&&y==b) {
                            System.out.println(cnt);
                            break;
                        } else {
                            System.out.println(cnt);
                            break;
                        }
                    } else {
                        long diff = x-a;
                        if (diff<0) {
                            System.out.println(-1);
                            break;
                        }
                        long k = diff/y;
                        if (k==0) {
                            System.out.println(-1);
                            break;
                        }
                        cnt+=k;
                        x-=k*y;
                    }
                } else {
                    if (x<a) {
                        System.out.println(-1);
                        break;
                    }
                    if (x==a) {
                        long diff = y-b;
                        if (diff<0) {
                            System.out.println(-1);
                            break;
                        }
                        if (diff%x!=0) {
                            System.out.println(-1);
                            break;
                        }
                        cnt+=diff/x;
                        y=b;
                        if (x==a&&y==b) {
                            System.out.println(cnt);
                            break;
                        } else {
                            System.out.println(cnt);
                            break;
                        }
                    } else {
                        long diff = y-b;
                        if (diff<0) {
                            System.out.println(-1);
                            break;
                        }
                        long k = diff/x;
                        if (k==0) {
                            System.out.println(-1);
                            break;
                        }
                        cnt+=k;
                        y-=k*x;
                    }
                }
            }
        }
    }
}