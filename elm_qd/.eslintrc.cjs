/* eslint-env node */
require('@rushstack/eslint-patch/modern-module-resolution')

module.exports = {
  root: true,
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
    '@vue/eslint-config-prettier/skip-formatting'
  ],
  parserOptions: {
    ecmaVersion: 'latest'
  },
  'vue/multi-word-component-names': [
    0,
    {
      ignores: ['index'] // vue组件名称多单词组成(忽略index. vue)
    }
  ]
}
