package iwium;

import robocode.AdvancedRobot;

/**
 * Created by Dawid on 2015-06-13.
 */
public class Action
{
    private ActionType actionType;
    private double actionParameter;
    private AdvancedRobot robot;

    public Action(ActionType actionType, double actionParameter)
    {
        this.actionType = actionType;
        this.actionParameter = actionParameter;
    }

    public void setRobot(AdvancedRobot robot)
    {
        this.robot = robot;
    }

    public void performAction()
    {
        if(this.robot != null)
        {
            switch(this.actionType)
            {
                case AHEAD:
                    this.robot.setAhead(actionParameter);
                    break;
                case BACK:
                    this.robot.setBack(actionParameter);
                    break;
                case STOP:
                    this.robot.stop();
                    break;
                case FIRE:
                    this.robot.fire(actionParameter);
                    break;
                case TURN_GUN_LEFT:
                    this.robot.turnGunLeft(actionParameter);
                    break;
                case TURN_GUN_RIGHT:
                    this.robot.turnGunRight(actionParameter);
                    break;
                case TURN_RADAR_LEFT:
                    this.robot.turnRadarLeft(actionParameter);
                    break;
                case TURN_RADAR_RIGHT:
                    this.robot.turnRadarRight(actionParameter);
                    break;
                case TURN_LEFT:
                    this.robot.turnLeft(actionParameter);
                    break;
                case TURN_RIGHT:
                    this.robot.turnRight(actionParameter);
                    break;
            }
        }
    }
}
