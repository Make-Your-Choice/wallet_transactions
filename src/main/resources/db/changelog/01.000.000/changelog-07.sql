create table wallet (
    id_wallet serial primary key,
    residue numeric
);

create table wallet_transaction (
    id_wallet_transaction serial primary key,
    id_wallet integer references wallet,
    transaction_type numeric,
    amount numeric
);