#!/usr/bin/python
# -*- coding: UTF-8 -*-

from xml.dom.minidom import parse
import xml.dom.minidom
import xlwt


# 获取对应语言的所有Key
def getAllKeysByLanguage(language):
    stringFilePath = "src/main/res/" + language + "/strings.xml"
    DOMTree = xml.dom.minidom.parse(stringFilePath)
    collection = DOMTree.documentElement
    strings = collection.getElementsByTagName("string")
    keys = [element.getAttribute("name") for element in strings]
    return keys


def writeOneLanguageString(sheet, language, col):
    stringFilePath = "src/main/res/" + language + "/strings.xml"
    DOMTree = xml.dom.minidom.parse(stringFilePath)
    collection = DOMTree.documentElement
    strings = collection.getElementsByTagName("string")

    print("Size: %s" % len(strings))

    # for row in range(0, len(keys)):
    #     for index in range(0, len(strings) + 1):
    #         key1 = keys[row].getAttribute("name")
    #         key2 = strings[index].getAttribute("name")
    #         value = strings[index].childNodes[0].data
    #         if  key1 == key2 :
    #             print("%s, %s, %s" % (key1, key2, value))
    #             sheet.write(row + 1, col + 1, value)
    #             break

    for row in range(1, len(strings) + 1):
        # print("key=%s" % strings[row - 1].getAttribute("name"))
        node = strings[row - 1].childNodes[0]
        if node:
            sheet.write(row, col + 1, node.data)

languages = ["values-zh-rTW", "values-en"]

DOMTree = xml.dom.minidom.parse("src/main/res/values-zh-rHK/strings.xml")
collection = DOMTree.documentElement

keys = collection.getElementsByTagName("string")

# for movie in values:
#     name = movie.getAttribute("name")
#     value = movie.childNodes[0].data
#     print("Name:%s, Value=%s" % (name, value))

xcelPath = "Language.xls"

wb = xlwt.Workbook()

sheet = wb.add_sheet('sheet1', cell_overwrite_ok=True)  # 创建sheet

# 写表格第一栏(国家码)
sheet.write(0, 0, 'key')
for index in range(0, len(languages) + 1):
    value = ''
    if index == 0:
        value = "key"
    else:
        value = languages[index - 1]
    sheet.write(0, index, value)

for row in range(1, len(keys) + 1):
    sheet.write(row, 0, keys[row - 1].getAttribute("name"))

# 分别写入每种语言的值
for language in languages:
    writeOneLanguageString(sheet, language, languages.index(language))

wb.save(xcelPath)

print("写入数据成功")
