export const LOCAL_BASE_URL = (process.env.NODE_ENV !== 'production') ? 'http://localhost:8080' : 'app.com';

export const getCustomers = () => (`${LOCAL_BASE_URL}/customers`);

export const searchCustomers = (query) => (`${LOCAL_BASE_URL}/customers?search=${query}`);

export const sortCustomers = (sortBy, sortDir) => (`${LOCAL_BASE_URL}/customers?sortBy=${sortBy}&sortDir=${sortDir}`);

export const seedDb = (numCustomers) => (`${LOCAL_BASE_URL}/customers/seedDb?numCustomers=${numCustomers}`);