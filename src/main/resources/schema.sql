CREATE TABLE contracts
(
    id       SERIAL PRIMARY KEY,
    name     CHARACTER VARYING(10) NOT NULL,
    subject  CHARACTER VARYING(30) NOT NULL,
    sum      DECIMAL               NOT NULL,
    comments CHARACTER VARYING(50) NOT NULL,
    date     DATE                  NOT NULL
);

CREATE TABLE customers
(
    id           SERIAL PRIMARY KEY,
    name         CHARACTER VARYING(75) NOT NULL,
    address      CHARACTER VARYING(75) NOT NULL,
    tax_id       INTEGER               NOT NULL,
    phone_number CHARACTER VARYING(30) NOT NULL,
    email        CHARACTER VARYING(30) NOT NULL,
    contract_id  LONG                  NOT NULL,
    FOREIGN KEY (contract_id) REFERENCES contracts (id)
);

CREATE TABLE payments
(
    id          SERIAL PRIMARY KEY,
    amount      DECIMAL NOT NULL,
    time        TIMESTAMP DEFAULT NOW(),
    contract_id LONG    NOT NULL,
    customer_id LONG    NOT NULL,
    FOREIGN KEY (contract_id) REFERENCES contracts (id)
);
