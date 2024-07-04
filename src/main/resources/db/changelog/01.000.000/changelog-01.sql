create table wallet (
    id_wallet serial primary key,
    residue numeric(15, 12)
);

create table transaction_type (
    id_transaction_type serial primary key,
    name varchar(100)
);

create table wallet_transaction (
    id_wallet_transaction serial primary key,
    id_wallet integer references wallet,
    id_transaction_type integer references transaction_type,
    amount numeric(15, 12)
);