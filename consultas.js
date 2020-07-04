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

// 2do
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
                        "month" : 9.0, 
                        "day" : 6.0
                    }
                }
            }
        }, 
        { 
            "$match" : { 
                "cliente.obraSocial" : { 
                    "$exists" : true
                }
            }
        }, 
        { 
            "$group" : { 
                "_id" : { 
                    "nroSucursal" : { 
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
                    "obraSocial" : "$cliente.obraSocial.nombre"
                }, 
                "detallesVentas" : { 
                    "$push" : "$detalleVentas"
                }, 
                "total" : { 
                    "$sum" : "$precioTotal"
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id" : 1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);

// 3ro
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
                        "month" : 9.0, 
                        "day" : 6.0
                    }
                }
            }
        }, 
        { 
            "$group" : { 
                "_id" : { 
                    "nroSucursal" : { 
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
                    "formaPago" : "$formaPago"
                }, 
                "detallesVentas" : { 
                    "$push" : "$detalleVentas"
                }, 
                "total" : { 
                    "$sum" : "$precioTotal"
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id" : 1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);

// El 4to
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
                        "month" : 4.0, 
                        "day" : 6.0
                    }
                }
            }
        }, 
        { 
            "$unwind" : "$detalleVentas"
        }, 
        { 
            "$project" : { 
                "_id" : 0.0, 
                "nroSucursal" : { 
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
                "detalleVentas" : 1.0
            }
        }, 
        { 
            "$group" : { 
                "_id" : "$nroSucursal", 
                "nroSucursal" : { 
                    "$first" : "$nroSucursal"
                }, 
                "detalleVentas" : { 
                    "$push" : "$detalleVentas"
                }
            }
        }, 
        { 
            "$unwind" : "$detalleVentas"
        }, 
        { 
            "$group" : { 
                "_id" : "$detalleVentas.producto.codigo", 
                "producto" : { 
                    "$first" : "$detalleVentas.producto"
                }, 
                "sucursalVendio" : { 
                    "$push" : { 
                        "sucursal" : "$nroSucursal", 
                        "cantidad" : "$detalleVentas.cantidad"
                    }
                }
            }
        }, 
        { 
            "$project" : { 
                "_id" : null, 
                "codProducto" : "$_id", 
                "producto" : 1.0, 
                "sucursalVendio" : 1.0
            }
        }, 
        { 
            "$group" : { 
                "_id" : "$producto.tipoProducto", 
                "productos" : { 
                    "$push" : { 
                        "producto" : "$producto", 
                        "sucursalVendio" : "$sucursalVendio"
                    }
                }
            }
        }, 
        { 
            "$project" : { 
                "_id" : 0.0, 
                "tipoProducto" : "$_id", 
                "productos" : 1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);

// El 5to
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
                        "month" : 9.0, 
                        "day" : 6.0
                    }
                }
            }
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
                "detallesVentas" : { 
                    "$push" : "$detalleVentas"
                }, 
                "total" : { 
                    "$sum" : "$precioTotal"
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id" : 1.0
            }
        }, 
        { 
            "$unwind" : "$detallesVentas"
        }, 
        { 
            "$unwind" : "$detallesVentas"
        }, 
        { 
            "$group" : { 
                "_id" : { 
                    "nroSucursal" : "$_id", 
                    "producto" : "$detallesVentas.producto.codigo"
                }, 
                "total" : { 
                    "$sum" : { 
                        "$multiply" : [
                            "$detallesVentas.producto.precio", 
                            "$detallesVentas.cantidad"
                        ]
                    }
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id.nroSucursal" : 1.0, 
                "total" : -1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);

// 6to
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
                        "month" : 9.0, 
                        "day" : 6.0
                    }
                }
            }
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
                "detallesVentas" : { 
                    "$push" : "$detalleVentas"
                }, 
                "total" : { 
                    "$sum" : "$precioTotal"
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id" : 1.0
            }
        }, 
        { 
            "$unwind" : "$detallesVentas"
        }, 
        { 
            "$unwind" : "$detallesVentas"
        }, 
        { 
            "$group" : { 
                "_id" : { 
                    "nroSucursal" : "$_id", 
                    "producto" : "$detallesVentas.producto.codigo"
                }, 
                "total" : { 
                    "$sum" : "$detallesVentas.cantidad"
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id.nroSucursal" : 1.0, 
                "total" : -1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);

// 7mo
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
                        "month" : 9.0, 
                        "day" : 6.0
                    }
                }
            }
        }, 
        { 
            "$group" : { 
                "_id" : { 
                    "nroSucursal" : { 
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
                    "cliente" : "$cliente"
                }, 
                "total" : { 
                    "$sum" : "$precioTotal"
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id" : 1.0
            }
        }, 
        { 
            "$sort" : { 
                "_id.nroSucursal" : 1.0, 
                "total" : -1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);

// 8vo
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
                        "month" : 4.0, 
                        "day" : 6.0
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
                    "nroSucursal" : { 
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
                    "cliente" : "$cliente"
                }, 
                "total" : { 
                    "$sum" : "$detalleVentas.cantidad"
                }
            }
        }, 
        { 
            "$sort" : { 
                "_id" : 1.0
            }
        }, 
        { 
            "$sort" : { 
                "_id.nroSucursal" : 1.0, 
                "total" : -1.0
            }
        }
    ], 
    { 
        "allowDiskUse" : false
    }
);
