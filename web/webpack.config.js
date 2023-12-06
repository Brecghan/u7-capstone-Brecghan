const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    trainingMatrixHome: path.resolve(__dirname, 'src', 'pages', 'trainingMatrixHome.js'),
    trainingsHome: path.resolve(__dirname, 'src', 'pages', 'trainingsHome.js'),
    employeesHome: path.resolve(__dirname, 'src', 'pages', 'employeesHome.js'),
    testsHome: path.resolve(__dirname, 'src', 'pages', 'testsHome.js'),
    createEmployee: path.resolve(__dirname, 'src', 'pages', 'createEmployee.js'),
    createTraining: path.resolve(__dirname, 'src', 'pages', 'createTraining.js'),
    employee: path.resolve(__dirname, 'src', 'pages', 'employee.js'),
    training: path.resolve(__dirname, 'src', 'pages', 'training.js'),
    test: path.resolve(__dirname, 'src', 'pages', 'test.js'),
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}
