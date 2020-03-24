# INFORMATION
The plugin lets you colour signs and create a parkour sign that can be claimed daily. It saves who has clicked the sign in memory and in the data.yml by adding UUIDs to an array. When it's a new day, it clears everyone from the array.

The sign must be spelled out line by line, exactly how it's set up in the config.yml

`reward.command: "eco add {user} 10"` makes console run `/eco add noobcrew 10`. (Do not include a /)

The file `Gsound.java` is there purely to support versions up to 1.14. The native version is **1.8**, but works on **1.12** too.

### Permission Nodes
`signs.color` let's you colour signs

`signs.parkour` let's you create the parkour sign
