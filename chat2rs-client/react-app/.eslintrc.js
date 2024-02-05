const fs = require('fs');
const path = require('path');

const prettierOptions = JSON.parse(fs.readFileSync(path.resolve(__dirname, '.prettierrc'), 'utf8'));

module.exports = {
  extends: ['plugin:react/recommended', 'plugin:react-hooks/recommended', 'prettier'],
  plugins: ['@typescript-eslint'],
  parserOptions: {
    project: './tsconfig.json',
    requireConfigFile: false,
    tsconfigRootDir: __dirname,
    ecmaVersion: 6,
  },
  rules: {
    // 'prettier/prettier': ['error', prettierOptions],
    'react/jsx-uses-react': 'off',
    'react/react-in-jsx-scope': 'off',
    '@typescript-eslint/naming-convention': 'off',
    'react/static-property-placement': 'off',
    // 'import/no-cycle': 'warn',
    'prefer-promise-reject-errors': 'off'
  }
};
