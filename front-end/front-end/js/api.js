// API Utilities for Virtual Banking System
const API_CONFIG = {
    BASE_URL: 'http://localhost:9099', // BFF Service URL
    ENDPOINTS: {
        LOGIN: '/users/login',
        REGISTER: '/users/register',
        USER_PROFILE: '/users/{userId}/profile',
        GET_USER_ACCOUNTS: '/users/{userId}/accounts',
        GET_ACCOUNT_DETAILS: '/accounts/{accountId}',
        CREATE_ACCOUNT: '/accounts',
        TRANSFER: '/accounts/transfer',
        TRANSACTIONS: '/transactions/transfer/initiation',
        EXECUTE_TRANSACTION: '/transactions/transfer/execution'
    },
    HEADERS: {
        'Content-Type': 'application/json'
    }
};

// Handle API calls
class ApiService {
    // Generic method to make API calls
    static async fetchApi(endpoint, method = 'GET', body = null, params = {}) {
        try {
            const url = this.formatUrl(endpoint, params);
            
            const options = {
                method,
                headers: {
                    ...API_CONFIG.HEADERS,
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
                    'Access-Control-Allow-Headers': 'Content-Type, Authorization'
                },
                credentials: 'include' // Include cookies for session management
            };

            if (body) {
                options.body = JSON.stringify(body);
            }

            const response = await fetch(url, options);
            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || `Error: ${response.status} ${response.statusText}`);
            }

            return data;
        } catch (error) {
            console.error('API Error:', error);
            // Add more detailed error information to help debug
            if (error.message === 'Failed to fetch') {
                throw new Error('Connection error: Could not connect to the server. Make sure all services are running.');
            }
            throw error;
        }
    }

    // Format URL with path parameters
    static formatUrl(endpoint, params) {
        let url = `${API_CONFIG.BASE_URL}${endpoint}`;
        
        // Replace path parameters
        for (const [key, value] of Object.entries(params)) {
            url = url.replace(`{${key}}`, value);
        }
        
        return url;
    }

    // Authentication methods
    static async login(username, password) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.LOGIN, 'POST', {
            username,
            password
        });
    }

    static async register(userData) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.REGISTER, 'POST', userData);
    }

    static async getUserProfile(userId) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.USER_PROFILE, 'GET', null, { userId });
    }

    // Account methods
    static async getUserAccounts(userId) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.GET_USER_ACCOUNTS, 'GET', null, { userId });
    }

    static async getAccountDetails(accountId) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.GET_ACCOUNT_DETAILS, 'GET', null, { accountId });
    }

    static async createAccount(accountData) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.CREATE_ACCOUNT, 'POST', accountData);
    }

    // Transaction methods
    static async transferFunds(transferData) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.TRANSFER, 'POST', transferData);
    }
    
    static async initiateTransaction(transactionData) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.TRANSACTIONS, 'POST', transactionData);
    }
    
    static async executeTransaction(transactionData) {
        return this.fetchApi(API_CONFIG.ENDPOINTS.EXECUTE_TRANSACTION, 'POST', transactionData);
    }
}

// Session management
class SessionManager {
    static saveUserSession(userData) {
        localStorage.setItem('user', JSON.stringify(userData));
    }

    static getUserSession() {
        const userData = localStorage.getItem('user');
        return userData ? JSON.parse(userData) : null;
    }

    static clearUserSession() {
        localStorage.removeItem('user');
    }

    static isLoggedIn() {
        return !!this.getUserSession();
    }

    static redirectIfNotLoggedIn() {
        if (!this.isLoggedIn()) {
            window.location.href = 'Ejada Login Form.html';
            return false;
        }
        return true;
    }

    static redirectIfLoggedIn() {
        if (this.isLoggedIn()) {
            window.location.href = 'Ejada Empty Accounts.html';
            return true;
        }
        return false;
    }
}
