package TpSMA;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MainAgent extends Agent {
    AgentController AgentDeb;
    AgentController AgentFin ;
    
    @Override
    protected void takeDown() {
        System.out.println(getLocalName() + ": Good Bye");
    }

    @Override
    protected void setup() {
        try {
            Object[] args = getArguments();
            AgentContainer ac = (AgentContainer) args[0];
            int tab[] = (int[]) args[1];
            int valeur=(int) args[2];
          
            if (tab.length == 0) {
                System.out.println("empty table.");
                doDelete();
            } else if (tab.length == 1) {
                System.out.println("AgentDeb is start successfully");
                AgentDeb=ac.createNewAgent("AgentDeb", "TpSMA.AgentsHandler", new Object[]{tab,valeur});
                AgentDeb.start();
            } else {
                // length of table is >1
                int [] firsthalf=Arrays.copyOfRange(tab, 0, (tab.length+1)/2);
                int [] secondhalf=Arrays.copyOfRange(tab, (tab.length+1)/2,tab.length);
                 
                AgentDeb=ac.createNewAgent("AgentDeb", "TpSMA.AgentsHandler", new Object[]{firsthalf,valeur});
                AgentDeb.start();
                System.out.println("AgentDeb is start successfully");
                AgentFin= ac.createNewAgent("AgentFin", "TpSMA.AgentsHandler", new Object[]{secondhalf,valeur,firsthalf.length});
                AgentFin.start();
                System.out.println("AgentFin is start successfully");
            }
            addBehaviour(new CyclicBehaviour(){
                @Override
                public void action() {
                    ACLMessage msg=receive();
                    if (msg!=null){
                        
                        JOptionPane.showMessageDialog(null, "Message recived "+msg.getContent());   
                        try {
                            AgentDeb.kill();
                            if(tab.length >1) 
                                AgentFin.kill();
                        } catch (StaleProxyException ex) {
                            Logger.getLogger(MainAgent.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else block();
                }
            });
        } catch (StaleProxyException ex) {
            Logger.getLogger(MainAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
