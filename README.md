# QueueAPI
This is an API to easily create queues for Spigot servers.
## Usage
1. Install the api to your local maven repo using `maven install`
2. Add the dependency to your Spigot plugin and set the scope to compile.
## Example
Create your own queue for players using the PlayerQueue class.
```java
QueueAPI.createQueue(PlayerQueue.builder()
        // Set the name of the queue
        .name("myQueue")
        // Set the number of teams
        .teamCount(2)
        // Set the size of each team
        .teamSize(1)
        // Set the action to be executed when something happens to the queue
        .queueAction(new QueueAction() {
            @Override
            public void onJoin(Player player) {
                player.sendMessage(ChatColor.GREEN + "You joined the queue!");
            }

            @Override
            public void onLeave(Player player) {
                player.sendMessage(ChatColor.RED + "You left the queue!");
            }

            @Override
            public void onFill(List<AbstractQueue<Player>.Team> teams) {
                int teamCounter = 0;
                for (AbstractQueue<Player>.Team team : teams) {
                    for (Player player : team.getMembers()) {
                        player.sendMessage(ChatColor.YELLOW + "You are in team: " + teamCounter);
                    }
                teamCounter++;
            }
        }
    }).build());

// Get a queue that has been created
PlayerQueue queue = QueueAPI.getQueue("myQueue");

// Add a player to a queue
QueueAPI.joinQueue("myQueue", player);

// Remove a player from a queue
QueueAPI.leaveQueue("myQueue", player);

// Delete a queue to prevent players from joining it
QueueAPI.deleteQueue("myQueue");
```
Or even create queues for a specific object eg. `UUID` and have full control of storing your own queues.
```java
public class UUIDQueue extends AbstractQueue<UUID> {

    @Override
    public String getName() {
        return "UUIDQueue";
    }

    @Override
    public int getTeamSize() {
        return 4;
    }

    @Override
    public int getTeamCount() {
        return 2;
    }

    @Override
    public void onJoin(UUID uuid) {
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN + "You joined the queue!");
    }

    @Override
    public void onLeave(UUID uuid) {
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + "You left the queue!");
    }

    @Override
    public void onFill(List<AbstractQueue<UUID>.Team> teams) {
        int teamCounter = 0;
        for (AbstractQueue<UUID>.Team team : teams) {
            for (UUID uuid : team.getMembers()) {
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW + "You are in team: " + teamCounter);
            }
            teamCounter++;
        }
    }
}
```
