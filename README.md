# QueueAPI
This is an API to easily create queues for Spigot servers
## Usage
1. Install the api to your local maven repo using `maven install`
2. Add the dependency to your Spigot plugin.
## Example
```java
QueueAPI.createQueue(Queue.builder()
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
            public void onFill(List<Queue.Team> teams) {
                int teamCounter = 0;
                for (Queue.Team team : teams) {
                    for (Player player : team.getPlayers()) {
                        player.sendMessage(ChatColor.YELLOW + "You are in team: " + teamCounter);
                    }
                    teamCounter++;
                }
            }
        }).build());

// Get a queue that has been created
Queue queue = QueueAPI.getQueue("myQueue");

// Add a player to a queue
QueueAPI.joinQueue("myQueue", player);

// Remove a player from a queue
QueueAPI.leaveQueue("myQueue", player);

// Delete a queue to prevent players from joining it
QueueAPI.deleteQueue("myQueue");
```
