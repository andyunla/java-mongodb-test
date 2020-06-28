db.collection.find({"fecha":{ $gte:{"year": 2020, "month": 1, "day": 27}, $lte:{"year":2020, "month": 6, "day":1}}}).count();

db.getCollection("ventas").aggregate([
    { $match: {
        "fecha":{ $gte:{"year": 2020, "month": 1, "day": 1}, $lte:{"year":2020, "month": 6, "day":1}}}
    },
    { $unwind: "$detalleVentas" },
    { $group : {
        _id : "$nroTicket",
        "Total Venta" : {$sum : "$detalleVentas.subTotal"}
    }},
    { $sort : { _id : 1 } }
])
