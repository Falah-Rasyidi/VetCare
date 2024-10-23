DROP TABLE IF EXISTS shippingaddress;
CREATE TABLE shippingaddress
(
    shippingAddressId  INT PRIMARY KEY AUTO_INCREMENT,
    userId             INT          not null,
    firstName          VARCHAR(255) not null,
    lastName           VARCHAR(255) not null,
    address            VARCHAR(255) not null,
    citySuburbProvince VARCHAR(255),
    state              VARCHAR(255) not null,
    postalCode         VARCHAR(15)  not null,
    country            VARCHAR(255) not null,
    phoneNumber        varchar(255),
    FOREIGN KEY (userId) REFERENCES userAccount (userId) ON DELETE CASCADE
);