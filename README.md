# QueueAPI
This is an API to easily create queues for Spigot servers.
## Usage
1. Install the api to your local maven repo using `maven install`
2. Add this dependency to your Spigot plugin and set the scope to compile.
```xml
<dependency>
    <groupId>com.quadflame</groupId>
    <artifactId>QueueAPI</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```
## Example
Create your own queue for players using the PlayerQueue class.
```java
// Create a queue using Bukkit Players
QueueAPI.createQueue(new PlayerQueue("MyQueue", 1, 2, new QueueAction<Player>() {
    @Override
    public void onJoin(Player player) {
        player.sendMessage(ChatColor.GREEN + "You joined the queue!");
    }

    @Override
    public void onLeave(Player player) {
        player.sendMessage(ChatColor.RED + "You left the queue!");
    }

    @Override
    public void onFill(List<Team<Player>> teams) {
        int teamCounter = 0;
        for (Team<Player> team : teams) {
            for (Player player : team.getMembers()) {
                player.sendMessage(ChatColor.YELLOW + "You are in team: " + teamCounter);
            }
            teamCounter++;
        }
    }
}));

// Get a PlayerQueue
PlayerQueue playerQueue = QueueAPI.getPlayerQueue("MyQueue");

// Add a player to a queue
QueueAPI.getPlayerQueue("MyQueue").join(player);

// Remove a player from a queue
QueueAPI.getPlayerQueue("MyQueue").remove(player);

// Delete a queue to prevent players from joining it
QueueAPI.deleteQueue("MyQueue");

// Get any type of queue
AbstractQueue<?> otherQueue = QueueAPI.getQueue("OtherQueue");
```
Or even create queues for a specific object eg. `UUID` and have full control of storing your own queues.
```java
public class TestQueue extends AbstractQueue<UUID> {
    @Override
    public String getName() {
        return "TestQueue";
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
    public void onFill(List<Team<UUID>> teams) {
        int teamCounter = 0;
        for (Team<UUID> team : teams) {
            for (UUID uuid : team.getMembers()) {
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW + "You are in team: " + teamCounter);
            }
            teamCounter++;
        }
    }
}
```
Alternatively, this has the same functionality as the previous example:
```java
QueueAPI.createQueue(new AbstractQueue.Builder<UUID>()
        // Set the name of the queue
        .name("myQueue")
        // Set the number of teams
        .teamCount(2)
        // Set the size of each team
        .teamSize(4)
        // Set the action to be executed when something happens to the queue
        .queueAction(new QueueAction<UUID>() {  
            // This is called when a player joins the queue
            @Override
            public void onJoin(UUID uuid) {
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN + "You joined the queue!");
            }   
            // This is called when a player leaves the queue
            @Override
            public void onLeave(UUID uuid) {
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + "You left the queue!");
            }   
            // This is called when the queue is full
            @Override
            public void onFill(List<Team<UUID>> teams) {
                int teamCounter = 0;
                for (Team<UUID> team : teams) {
                    for (UUID uuid : team.getMembers()) {
                        Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW + "You are in team: " + teamCounter);
                    }
                    teamCounter++;
                }
            }
        }).build());
```
