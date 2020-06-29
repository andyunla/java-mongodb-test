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

// Para obtener las ventas por cada local
db.getCollection("ventas").aggregate([
    { $match: {
        "fecha":{ $gte:{"year": 2020, "month": 1, "day": 1}, $lte:{"year":2020, "month": 6, "day":30}}}
    },
    { $unwind: "$detalleVentas" }, 
    {
        $group: {
            _id: {
                $substr: ["$nroTicket", 0, {
                    $indexOfBytes: ["$nroTicket", "-"]
                }]
            },
            productos: { $push:  "$detalleVentas" },
            total: {$sum : "$detalleVentas.subTotal"}
        }
    },
    {
        $sort: { '_id': 1 }
    }, 
    {
        $project: {
            _id: 0,
            'productos': 1,
            'nroSucursal': "$_id",
            'total': 1
        }
    }
])

// La 1ra completa
db.getCollection("ventas").aggregate(
    [
        { 
            "$match" : { 
                "fecha" : { 
                    "$gte" : { 
                        "year" : 2020.0, 
                        "month" : 1.0, 
                        "day" : 1.0
                    }, 
                    "$lte" : { 
                        "year" : 2020.0, 
                        "month" : 6.0, 
                        "day" : 30.0
                    }
                }
            }
        }, 
        { 
            "$unwind" : "$detalleVentas"
        }, 
        { 
            "$group" : { 
                "_id" : { 
                    "$substr" : [
                        "$nroTicket", 
                        0.0, 
                        { 
                            "$indexOfBytes" : [
                                "$nroTicket", 
                                "-"
                            ]
                        }
                    ]
                }, 
                "detalles" : { 
                    "$push" : "$detalleVentas"
                }, 
                "totalSucursal" : { 
                    "$sum" : "$detalleVentas.subTotal"
                }
            }
        }, 
        { 
            "$project" : { 
                "_id" : 0.0, 
                "nroSucursal" : "$_id", 
                "detalles" : 1.0, 
                "totalSucursal" : 1.0
            }
        }, 
        { 
            "$sort" : { 
                "nroSucursal" : 1.0
            }
        }, 
        { 
            "$group" : { 
                "_id" : null, 
                "ventasSucursales" : { 
                    "$push" : "$$ROOT"
                }, 
                "totalTodo" : { 
                    "$sum" : "$total"
                }
            }
        }, 
        { 
            "$project" : { 
                "_id" : 0.0, 
                "ventasSucursales" : 1.0, 
                "totalTodo" : 1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);

