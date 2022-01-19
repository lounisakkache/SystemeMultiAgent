
package TpSMA;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentsHandler extends Agent {    
    @Override
    protected void takeDown(){ 
        System.out.println(getLocalName()+": Good Bye");
    }
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour(){
            boolean found=false;
            @Override
            public void action() {
                Object[] args = getArguments();
                int valeur=(int) args[1];
                if("AgentDeb".equals(getLocalName())){
                    int firsthalf[] = (int[]) args[0];
                    for(int i=0;i<=firsthalf.length-1 && !found ;i++){
                        if(firsthalf[i]==valeur){
                            found=true;
                            SendMsg(ACLMessage.INFORM,i,getLocalName());
                        }
                    }
                    if (!found){
                        SendMsg(ACLMessage.FAILURE,-10000,getLocalName());
                        found=true; // just for stopping Agent
                    }
                }else{//Second Agent
                   int secondhalf[] = (int[]) args[0];
                   int firsthalf_Len=(int) args[2];
                   
                   for (int i=secondhalf.length-1 ;i>=0 && !found;i--){
                       if(secondhalf[i]==valeur){
                            found=true;
                            SendMsg(ACLMessage.INFORM,firsthalf_Len+i,getLocalName());
                        }

                   }
                   if (!found){
                        SendMsg(ACLMessage.FAILURE,-10000,getLocalName());
                        found=true; // just for stopping Agent
                    }
                }
            }        
        });
    }
    
    private void SendMsg(int flag,int valeur,String AgentName){
        ACLMessage msg= new ACLMessage(flag);
        if(valeur!=-10000)         
            msg.setContent("valeur trouvre dans l'index = "+String.valueOf(valeur)+" par l'agent => "+AgentName );
        else 
            msg.setContent("valeur n'existe pas !! , par l'agent => "+AgentName );
        msg.addReceiver(new AID("MainAgent",AID.ISLOCALNAME));
        msg.setSender(getAID());
        send(msg);
    }
}
