[
    { $match: { "users.${user}": {"$exists" : true} } },
    { $project: { "id": 1, "proposedRole" : "$users.${user}.proposedRole", "projectRole" : "$users.${user}.projectRole", "user" : { $literal: "${user}" } } },
    { $group : { _id : "$user", projects: { $push: {"id" : "$_id", "proposedRole" : "$proposedRole", "projectRole" : "$projectRole" }} }},
    {$lookup:
        {
            from: "user",
            localField: "_id",
            foreignField: "_id",
            as: "user"
            }},
    {$unwind : "$user"}
]
