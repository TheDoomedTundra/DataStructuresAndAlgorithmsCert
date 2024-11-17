package Module1_ArrayLists.Recursion;
import java.util.ArrayList;

class IncreasingSubsequence{
    public static int findInLoop(int[] intList) {
        int numSubseq = 0;
        ArrayList<Integer> subseq = new ArrayList<>();
        // Cases:
        //  1. Find if the number is part of an increasing subsequence
        //  2. Keep track of the number of subsequences
        for (int i = 0; i < intList.length; i++) {
            int left = intList[i];
            subseq.add(left);
            for (int j = i + 1; j < intList.length; j++) {
                int right = intList[j];
                if ((right < left) || (right == left)) {
                    break;
                } else {
                    left = right;
                    subseq.add(left);
                }
            }

            if (subseq.size() > 1) {
                numSubseq++;
                System.out.println(subseq);
            }
            subseq.clear();

        }
        return numSubseq;
    }

    private static int findRecursiveImpl(int[] intList, int index, ArrayList<Integer> subseq) {
        int numSubseq = 0;

        for (int i = index; i < intList.length; i++) {
            if ((subseq.isEmpty()) || (intList[i] > subseq.get(subseq.size() - 1))) {
                subseq.add(intList[i]);
                numSubseq += findRecursiveImpl(intList, i + 1, subseq);
                if (index != 0) {
                    break;
                }
                if (subseq.size() > 1) {
                    System.out.println(subseq);
                    numSubseq++;
                }
                subseq.clear();
            } else {
                break;
            }
        }
        return numSubseq;
    }

    public static int findRecursive(int[] intList) {
        ArrayList<Integer> subseq = new ArrayList<>();
         return findRecursiveImpl(intList, 0, subseq);
    }

    public static void main(String[] args)
    {
        int[] exampleList = {1, 7, 3, 5, 2, 8, 10, 24, 24, -1, -5, 4};
        System.out.println("###### Find in a For Loop ######");
        System.out.println(findInLoop(exampleList));

        System.out.println("##### Find using Recursion #####");
        System.out.println(findRecursive(exampleList));

    }
}


// else if (subseq.size() > 1){
//     System.out.println("Full subsequence: ");
//     System.out.println(subseq);
//     subseq.clear();
//     numSubseq++;
//     break;
// }