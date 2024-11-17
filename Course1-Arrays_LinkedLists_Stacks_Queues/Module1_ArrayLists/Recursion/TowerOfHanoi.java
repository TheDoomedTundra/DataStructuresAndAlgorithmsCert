package Module1_ArrayLists.Recursion;

public class TowerOfHanoi {
    public static void solve(int n, char ta, char tb, char tc){
        //if we dont have any disks, then just end the function
        if(n == 0)
            return;

        /*
        now transfer n-1 (2 disks) from tower 1 to tower 3 using tower 2
         */
        solve(n-1, ta, tc, tb);

        //now transfer the third (nth) disk from tower a to b
        System.out.println(n+"["+ta+"->"+tb+"]");

        /*
        now just transfer those n-1 (2 disks) from tower c to tower b
         */
        solve(n-1, tc, tb, ta);
    }

    public static void main(String[] args) {
        solve(4, 'a', 'b', 'c');
    }
}
