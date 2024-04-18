import java.util.Scanner;

public class Application {
    //INITIALIZING Z CHART READER CLASS
    static ZChartReader reader=new ZChartReader("z-chart.txt");

    //CALCULATING HOLDING COST AND ANNUAL DEMAND
    public static float CalculateHoldingCost(float UnitCost, float InterestRate){
        return UnitCost*InterestRate/100;
    }
    public static float CalculateAnnualDemand(float LeadTime,float LeadTimeDemand){
        return (LeadTimeDemand*(12/LeadTime));
    }

    public static void main(String[] args) {
        //USER INPUT FOR VARIABLES
        Scanner scanner =new Scanner(System.in);

        System.out.println("Enter Unit Cost: ");
        float UnitCost = scanner.nextFloat();
        System.out.println("Enter Ordering Cost: ");
        float OrderingCost = scanner.nextFloat();
        System.out.println("Enter Penalty Cost: ");
        float PenaltyCost = scanner.nextFloat();
        System.out.println("Enter Annual Interest Rate: ");
        float InterestRate = scanner.nextFloat();
        System.out.println("Enter Lead Time: ");
        float LeadTime = scanner.nextFloat();
        System.out.println("Enter Lead Time Demand: ");
        float LeadTimeDemand = scanner.nextFloat();
        System.out.println("Enter Lead Time Standard Deviation: ");
        float LeadTimeStandardDeviation = scanner.nextFloat();

        float HoldingCost =CalculateHoldingCost(UnitCost,InterestRate);
        float AnnualDemand = CalculateAnnualDemand(LeadTime,LeadTimeDemand);

        //HARDCODED THE FIRST ITERATION FOR Q0 VALUE
        double Q0= Math.sqrt((2*OrderingCost*(12/LeadTime*LeadTimeDemand))/HoldingCost);
        double FR0=1-Q0*HoldingCost/(PenaltyCost*(12/LeadTime*LeadTimeDemand));
        double zValue=reader.getZValueFromF(FR0);
        double R0=zValue*OrderingCost+LeadTimeDemand;
        double nR0= reader.getLValueFromF(FR0)*OrderingCost;

        int counter=1;

        double Q=0;
        double FR;
        double zedValue;
        double R=0;
        double nR=nR0;
        double Qex=Q0;
        //ITERATION FOR OPTIMAL LOT SIZE AND REORDER POINT
        while (Q!=Qex){
            Qex=Q;
            Q=Math.sqrt(2*((12/LeadTime*LeadTimeDemand)*(OrderingCost+PenaltyCost*nR)/HoldingCost));
            FR=1-Q*HoldingCost/(PenaltyCost*(12/LeadTime*LeadTimeDemand));
            zedValue=reader.getZValueFromF(FR);
            R=zedValue*OrderingCost+LeadTimeDemand;
            nR=reader.getLValueFromF(FR)*OrderingCost;
            counter++;
        }
        System.out.println("Optimal Lot Size: "+Q);
        System.out.println("Reorder Point: "+R);
        System.out.println("Holding Cost: "+ HoldingCost);
        System.out.println("Annual Demand: "+ AnnualDemand);
        System.out.println("Total Iteration: "+counter);

    }



}
